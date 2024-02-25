import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Genetic {

        public static int populationSize = 500;
        public static int numGenerations = 100000;
        public static int mutationType = 1;
        public static int numGenerationSize = 1000;
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
                    System.out.println("Number of generation population Size: " + numGenerationSize);
                    System.out.println("Mutation type: " + mutationType + "\n");

                    System.out.println("1. Change population size");
                    System.out.println("2. Change number of generations");
                    System.out.println("3. Change number of generation population");
                    System.out.println("4. Change mutation type"); // Not sure if this option is to be implemented
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
                            while (true) {
                                System.out.println("Number of generations population size: ");
                                numGenerationSize = scanner.nextInt();
                                if (numGenerationSize <= 0) {
                                    System.out.println("The number of generation population size must be greater than 0");
                                    continue;
                                }
                                break;
                            }
                            break;
                        case 4:
                            while (true) {
                                System.out.println("Mutation type: ");
                                mutationType = scanner.nextInt();
                                if (mutationType < 1 || mutationType > 2) {
                                    System.out.println("The mutation type must be 1 or 2");
                                    continue;
                                }
                                break;
                            }
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


    private static void solve(Package[] packages) {
        Package[][] population = new Package[populationSize][packages.length]; // population of paths
        Package[] bestPath = new Package[packages.length];
        double bestCost = Integer.MAX_VALUE;
        int unchangedIterations = 0;
        int generation = 0;

        System.out.println(Annealing.getCost(packages));
        Package[] currentPath = packages.clone();

        // Generate initial population
        for (int i = 0; i < populationSize; i++) {
            Annealing.mutate(currentPath);
            population[i] = currentPath;
        }

        while (generation < numGenerations && unchangedIterations < numGenerationSize) {

            // Selection
            for (int i = 0; i < populationSize; i++) {
                if (Annealing.getCost(population[i]) < bestCost) {
                    bestPath = population[i];
                    bestCost = Annealing.getCost(population[i]);
                    unchangedIterations = 0;
                } else {
                    //Ignore this generation
                    unchangedIterations++;
                }
            }

            //CrossOver
            for (int i = 0; i < populationSize; i++) {
                int randomIndex1 = (int) (Math.random() * populationSize);
                int randomIndex2 = (int) (Math.random() * populationSize);
                Package[] parent1 = population[randomIndex1];
                Package[] parent2 = population[randomIndex2];
                int crossOverPoint = (int) (Math.random() * packages.length);
                Package[] child = new Package[packages.length];
                for (int j = 0; j < crossOverPoint; j++) {
                    child[j] = parent1[j];
                }
                for (int j = crossOverPoint; j < packages.length; j++) {
                    child[j] = parent2[j];
                }
                population[i] = child;
            }


            // Mutation
            for (int i = 0; i < populationSize; i++) {
                Annealing.mutate(bestPath);
                population[i] = bestPath;
            }

        }

        System.out.println("Best path: " + Arrays.toString(bestPath));
        System.out.println("Best Cost: " + bestCost);
        //writeStats(statsFile, bestCost);
        //writePath(pathFile, bestPath);
    }

}
