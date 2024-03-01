import Packages.Package;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Annealing {

    public static double startTemperature = 10_000;
    public static double coolingRate = 0.999;
    public static double endTemperature = 1;
    public static int mutationType = 1;
    public static int numUnchangedIterations = 1000;
    public static int numIterations;
    public static String statsFile = "stats.csv";
    public static String pathFile = "path.csv";

    public static void solveWithAnnealingMenu(Scanner scanner) {
        int option = 0;
        while (option != 7) {
            try {
                System.out.println("Solve with simulated annealing\n");
                System.out.println("Current configuration:");
                System.out.println("Start temperature: " + startTemperature);
                System.out.println("Cooling rate: " + coolingRate);
                System.out.println("End temperature: " + endTemperature);
                System.out.println("Number of unchanged iterations: " + numUnchangedIterations);
                System.out.println("Mutation type: " + mutationType + "\n");

                System.out.println("1. Change start temperature");
                System.out.println("2. Change end temperature");
                System.out.println("3. Change cooling rate");
                System.out.println("4. Change number of unchanged iterations");
                System.out.println("5. Change mutation type");
                System.out.println("6. Solve");
                System.out.println("7. Back");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        while (true) {
                            System.out.println("Start temperature: ");
                            startTemperature = scanner.nextDouble();
                            if (startTemperature <= 0) {
                                System.out.println("The start temperature must be greater than 0");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 2:
                        while (true) {
                            System.out.println("End temperature: ");
                            endTemperature = scanner.nextDouble();
                            if (endTemperature <= 0) {
                                System.out.println("The end temperature must be greater than 0");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 3:
                        while (true) {
                            System.out.println("Cooling rate: ");
                            coolingRate = scanner.nextDouble();
                            if (coolingRate <= 0 || coolingRate >= 1) {
                                System.out.println("The cooling rate must be between 0 and 1");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 4:
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
                    case 5:
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
                    case 6:
                        long startTime = System.currentTimeMillis();
                        solve(Main.packages);
                        long endTime = System.currentTimeMillis();
                        System.out.println("Execution time: " + (endTime - startTime) + "ms");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
            } catch (IOException e) {
                System.out.println("An error occurred while writing the file");
            }
        }
    }

    public static void mutate(Package[] newPath) {
        if (mutationType == 1) {
            int randomIndex1 = (int) (Math.random() * (newPath.length - 1));
            int randomIndex2 = (int) (Math.random() * (newPath.length - 1));

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
                Mutations.mutation1(newPath);
            } else if (random < 99) {
                Mutations.mutation2(newPath);
            } else {
                Mutations.mutation3(newPath);
            }
        }
    }

    public static Package[] solve(Package[] packages) throws IOException {
        BufferedWriter statsWriter = new BufferedWriter(new FileWriter(statsFile));
        BufferedWriter pathWriter = new BufferedWriter(new FileWriter(pathFile));

        double temperature = startTemperature;
        double bestCost = Package.getCost(packages);
        double currentCost = bestCost;

        Package[] bestPath = packages.clone();
        Package[] currentPath = packages;

        int iter = 0;

        statsWriter.write(String.join(",", "Iteration", "Best Cost", "Current Cost","Temperature"));
        statsWriter.newLine();

        int maxLastMutation = 0;
        int lastMutation = 0;

        while (lastMutation < numUnchangedIterations) {
            Package[] newPath = currentPath.clone();
            mutate(newPath);

            double newCost = Package.getCost(newPath);
            double delta = newCost - currentCost;

            lastMutation++;
            if (delta < 0 || Math.exp(-delta / temperature) > Math.random()) {
                currentPath = newPath;
                currentCost = newCost;
                lastMutation = 0;
            }

            maxLastMutation = Math.max(lastMutation, maxLastMutation);
            temperature *= 1 * coolingRate;
            iter++;

            if (currentCost < bestCost) {
                bestPath = currentPath;
                bestCost = currentCost;
            }

            if (iter % 1_000_000 == 0) {
                System.out.println("Iteration: " + iter + " Best cost: " + bestCost + " Current cost: " + currentCost + " Temperature: " + temperature
                        + " Max last mutation: " + maxLastMutation);
            }

        }

        numIterations = iter;


        pathWriter.write(String.join(",", Arrays.stream(currentPath).map(Object::toString).toArray(String[
                ]::new)));
        pathWriter.newLine();

        statsWriter.close();
        pathWriter.close();

        return bestPath;
    }

}