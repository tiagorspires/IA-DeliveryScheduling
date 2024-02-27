import Packages.Package;

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
        Package[][] population = new Package[populationSize + childrenSize][packages.length]; // population of paths
        Package[] bestPath = new Package[packages.length];
        double[] costs = new double[populationSize + childrenSize];
        double bestCost = Integer.MAX_VALUE;
        int generation = 0;

        // Generate  random initial population
        for (int i = 0; i < populationSize; i++) {
            population[i] = shuffle(packages.clone());
            costs[i] = Package.getCost(population[i]);
        }

        while (generation < numGenerations) {

            // Reproduction
            for (int i = 0; i < childrenSize; i++) {
                int parent1 = (int) (Math.random() * populationSize);
                int parent2 = (int) (Math.random() * populationSize);
                crossover(population[parent1], population[parent2], population[populationSize + i]);
                costs[populationSize + i] = Package.getCost(population[populationSize + i]);
            }

            // Mutation
            for (int i = populationSize; i < populationSize + childrenSize; i++) {
                if (Math.random() < mutationProb) {
                    int randomIndex1 = (int) (Math.random() * (packages.length - 1));
                    int randomIndex2 = (int) (Math.random() * (packages.length - 1));

                    Package temp = population[i][randomIndex1];
                    population[i][randomIndex1] = population[i][randomIndex2];
                    population[i][randomIndex2] = temp;

                    costs[i] = Package.getCost(population[i]);
                }
            }
            // 738 + 760 + 148 + 4776

            for (int i = populationSize; i < childrenSize; i++) {
                costs[i] = Package.getCost(population[i]);
            }
            //sort the first 100 population by cost
            for (int i = 0; i < populationSize + childrenSize; i++) {
                for (int j = i + 1; j < populationSize + childrenSize; j++) {
                    if (costs[i] > costs[j]) {
                        double temp = costs[i];
                        costs[i] = costs[j];
                        costs[j] = temp;

                        Package[] tempPath = population[i];
                        population[i] = population[j];
                        population[j] = tempPath;
                    }
                }
            }

            if (costs[0] < bestCost) {
                bestPath = population[0];
                bestCost = costs[0];
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
