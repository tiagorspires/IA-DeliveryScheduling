import Packages.Package;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TabuSearch {
    public static int mutationType = 2;
    public static int numUnchangedIterations = 10000;
    public static String statsFile = "stats.csv";
    public static String pathFile = "path.csv";
    private static int tenure = 250000;
    private static final Queue<Mutation> tabuList = new LinkedList<>();
    private static int randomIndex1 = 0;
    private static int randomIndex2 = 0;

    private static MutationType mutation;

    private enum MutationType {
        MUTATION1, MUTATION2, MUTATION3, MUTATION, NONE
    }

    private static class Mutation {
        private final MutationType mutation;
        private final int index1;
        private final int index2;

        public Mutation(MutationType mutation, int index1, int index2) {
            this.mutation = mutation;
            this.index1 = index1;
            this.index2 = index2;
        }

        public Mutation(){
            this.mutation = MutationType.NONE;
            this.index1 = 0;
            this.index2 = 0;
        }

        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Mutation m))
                return false;

            return m.mutation == mutation && m.index1 == index1 && m.index2 == index2;
        }
    }

    public static void solveWithTabuSearchMenu(Scanner scanner) {
        int option = 0;

        while (option != 5) {
            try {
                System.out.println("Solve with TabuSearch\n");
                System.out.println("Current configuration:");
                System.out.println("Mutation type: " + mutationType + "\n");
                System.out.println("Tenure: " + tenure + "\n");
                System.out.println("Number of unchanged iterations: " + numUnchangedIterations + "\n");
                System.out.println("1. Change mutation type");
                System.out.println("2. Change tenure");
                System.out.println("3. Change number of unchanged iterations");
                System.out.println("4. Solve");
                System.out.println("5. Back");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        while (true) {
                            System.out.println("Mutation type 1 or 2: ");
                            mutationType = scanner.nextInt();
                            if (mutationType != 1 && mutationType != 2) {
                                System.out.println("The mutation type must be 1 or 2");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 2:
                        while (true) {
                            System.out.println("Tenure: ");
                            tenure = scanner.nextInt();
                            if (tenure <= 0) {
                                System.out.println("The tenure must be greater than 0");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 3:
                        while (true) {
                            System.out.println("Number of unchanged iterations: ");
                            numUnchangedIterations = scanner.nextInt();
                            if (numUnchangedIterations <= 0) {
                                System.out.println("The number of unchanged iterations must be greater than 0");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 4:
                        long startTime = System.currentTimeMillis();
                        solve(Main.packages);
                        long endTime = System.currentTimeMillis();
                        System.out.println("Execution time: " + (endTime - startTime) + "ms");
                        break;
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }catch (IOException e) {
                System.out.println("An error occurred while writing the file");
            }
        }
    }

    public static void mutation1(Package[] newPath) {
        randomIndex1 = (int) (Math.random() * (newPath.length - 1));
        //index1 should be less than index2
        randomIndex2 = (int) (Math.random() * (newPath.length - randomIndex1 - 1) + randomIndex1 + 1);


        //move the elements between index1 and index2 one position to the right
        Package temp = newPath[randomIndex2];
        System.arraycopy(newPath, randomIndex1, newPath, randomIndex1 + 1, randomIndex2 - randomIndex1);
        //put the element in index1 in index2
        newPath[randomIndex1] = temp;
    }

    public static void mutation2(Package[] newPath) {
        randomIndex1 = (int) (Math.random() * (newPath.length - 2));
        randomIndex2 = (int) (Math.random() * ((newPath.length - randomIndex1) / 2));

        Package[] temp = Arrays.copyOfRange(newPath, randomIndex1, randomIndex1 + randomIndex2);

        System.arraycopy(newPath, randomIndex1 + randomIndex2, newPath, randomIndex1, randomIndex2);
        System.arraycopy(temp, 0, newPath, randomIndex1 + randomIndex2, randomIndex2);
    }

    public static void mutation3(Package[] newPath) {
        randomIndex1 = (int) (Math.random() * (newPath.length));
        randomIndex2 = (int) (Math.random() * (newPath.length - randomIndex1 - 1) + randomIndex1 + 1);

        //reverse the elements between index1 and index2
        for (int i = 0; i < (randomIndex2 - randomIndex1) / 2; i++) {
            Package temp = newPath[randomIndex1 + i];
            newPath[randomIndex1 + i] = newPath[randomIndex2 - i];
            newPath[randomIndex2 - i] = temp;
        }
    }


    public static void mutate(Package[] newPath) {
        if (mutationType == 1) {
            mutation = MutationType.MUTATION;
            randomIndex1 = (int) (Math.random() * (newPath.length - 1));
            randomIndex2 = (int) (Math.random() * (newPath.length - 1));

            Package temp = newPath[randomIndex1];
            newPath[randomIndex1] = newPath[randomIndex2];
            newPath[randomIndex2] = temp;
        } else {
            //probabilities:
            // mutation1 89
            // mutation2 10
            // mutation3 1
            int random = (int) (Math.random() * 100);
            if (random < 89) {
                mutation = MutationType.MUTATION1;
                mutation1(newPath);
            } else if (random < 99) {
                mutation = MutationType.MUTATION2;
                mutation2(newPath);
            } else {
                mutation = MutationType.MUTATION3;
                mutation3(newPath);
            }
        }
    }

    public static Package[] solve(Package[] packages) throws IOException {
        BufferedWriter statsWriter = new BufferedWriter(new FileWriter(statsFile));
        BufferedWriter pathWriter = new BufferedWriter(new FileWriter(pathFile));

        statsWriter.write(String.join(",", "Iteration", "Best Cost", "Current Cost","Temperature"));
        statsWriter.newLine();

        Package[] bestPath = packages.clone();
        Package[] currentPath = packages;
        double bestCost = Package.getCost(packages);
        double currentCost = bestCost;

        int iter = 0;
        int maxLastMutation = 0;
        int lastMutation = 0;

        while (lastMutation < numUnchangedIterations) {
            Package[] newPath = currentPath.clone();

            mutate(newPath);

            double newCost = Package.getCost(newPath);

            iter++;
            lastMutation++;
            if (newCost - currentCost < 0 ) {
                Mutation m = new Mutation(mutation, randomIndex1, randomIndex2);

                if (tabuList.contains(m)){
                    lastMutation++;
                    tabuList.add(new Mutation());
                    continue;
                }

                tabuList.add(m);

                if (tabuList.size() > tenure)
                    tabuList.remove();

                currentPath = newPath;
                currentCost = newCost;
                lastMutation = 0;
            }

            maxLastMutation = Math.max(lastMutation, maxLastMutation);

            if (newCost < bestCost) {
                bestCost = newCost;
                bestPath = newPath.clone();
            }

        }

        System.out.println("Best cost: " + Package.getCost(bestPath) + " Current cost: " + currentCost + " Max unchanged iterations: " + maxLastMutation);

        pathWriter.write(String.join(",", Arrays.stream(currentPath).map(Object::toString).toArray(String[]::new)));
        pathWriter.newLine();

        statsWriter.close();
        pathWriter.close();

        return bestPath;
    }

}
