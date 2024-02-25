import java.util.*;

public class Genetic {

        public static int populationSize = 100;
        public static int numGenerations = 1_000;
        public static double mutationProb = 0.2;
        public static int childrenSize = 100;
        public static String statsFile = "stats.csv";
        public static String pathFile = "path.csv";

        public static void solveWithGeneticMenu(Scanner scanner) {
            int option = 0;
            while (option != 6) {
                try {
                    System.out.println("Solve with genetic algorithm\n");
                    System.out.println("Current configuration:");
                    System.out.println("Population size: " + populationSize);
                    System.out.println("Number of generations: " + numGenerations);
                    System.out.println("Mutation Probability: " + mutationProb + "\n");

                    System.out.println("1. Change population size");
                    System.out.println("2. Change number of generations");
                    System.out.println("3. Change number of generation population");
                    System.out.println("4. Change mutation Probability [0-1]"); // Not sure if this option is to be implemented
                    System.out.println("5. Solve");
                    System.out.println("6. Back");
                    option = scanner.nextInt();

                    switch (option) {
                        case 1:
                            while (true) {
                                System.out.println("Population size: ");
                                populationSize = scanner.nextInt();
                                if (populationSize <= 0) {
                                    System.out.println("The population size must be greater than 0");
                                    continue;
                                }
                                break;
                            }
                            break;
                        case 2:
                            while (true) {
                                System.out.println("Number of generations: ");
                                numGenerations = scanner.nextInt();
                                if (numGenerations <= 0) {
                                    System.out.println("The number of generations must be greater than 0");
                                    continue;
                                }
                                break;
                            }
                            break;
                        case 3:
                        case 4:
                            break;
                        case 5:
                            long startTime = System.currentTimeMillis();
                            solve(Main.packages);
                            long endTime = System.currentTimeMillis();
                            System.out.println("Execution time: " + (endTime - startTime) + "ms");
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid option");
                    scanner.next();
                }
            }
        }

    public static Package[] shuffle(Package[] packages) {
        List<Package> list = Arrays.asList(packages);
        Collections.shuffle(list);
        return list.toArray(new Package[packages.length]);
    }

    public static void crossover(Package[] parent1, Package[] parent2, Package[] child){
        int index1 = (int) (Math.random() * (parent1.length - 2));
        int index2 = index1 + 2;

        //order based crossover
        List<Package> rest = new ArrayList<>(Arrays.asList(parent2));
        rest.removeAll(Arrays.asList(parent1).subList(index1, index2));
        int restIndex = 0;
        for(int i = 0; i < parent2.length; i++){
            if(i >= index1 && i < index2){
                child[i] = parent1[i];
            }else{
                child[i] = rest.get(restIndex);
                restIndex++;
            }
        }
    }

    public static void solve(Package[] packages) {
        ArrayList<Package[]> population = new ArrayList<>(populationSize); // population of paths
        Package[][] children = new Package[childrenSize][packages.length];
        Package[] bestPath = new Package[packages.length];
        double bestCost = Integer.MAX_VALUE;
        int generation = 0;

        System.out.println(Annealing.getCost(packages));

        // Generate  random initial population
        for (int i = 0; i < populationSize; i++) {
            population.add(shuffle(packages.clone()));
        }


        while (generation < numGenerations) {

            // Reproduction
            for (int i = 0; i < childrenSize; i++) {
                int parent1 = (int) (Math.random() * populationSize);
                int parent2 = (int) (Math.random() * populationSize);
                crossover(population.get(parent1), population.get(parent2), children[i]);
                population.add(children[i]);
            }

            // Mutation
            for (Package[] p : children) {
                if (Math.random() < mutationProb) {
                    int randomIndex1 = (int) (Math.random() * (p.length - 1));
                    int randomIndex2 = (int) (Math.random() * (p.length - 1));

                    Package temp = p[randomIndex1];
                    p[randomIndex1] = p[randomIndex2];
                    p[randomIndex2] = temp;
                }
            }
            // 738 + 760 + 148 + 4776

            population.sort(Comparator.comparingDouble(Annealing::getCost));
            population = new ArrayList<>(population.subList(0, populationSize));

            if (Annealing.getCost(population.get(0)) < bestCost) {
                bestPath = population.get(0);
                bestCost = Annealing.getCost(bestPath);
            }


            if (generation % 100 == 0)
                System.out.println("Selection done " + generation + " Best cost: " + bestCost);

            generation++;

        }

        System.out.println("Generation: " + generation);
        System.out.println("Best path: " + Arrays.toString(bestPath));
        System.out.println("Best Cost: " + bestCost);

    }

}
