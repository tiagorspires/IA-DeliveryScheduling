import Packages.Package;

import java.util.*;

public class Genetic {
    private static final SplittableRandom random = new SplittableRandom();
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

    public static Package[] crossover(Package[] parent1, Package[] parent2){
        Package[] child = new Package[parent1.length];

        int index1 = random.nextInt(parent1.length - 2);
        int index2 = index1 + 2;

        //order based crossover
        Package crossover1 = parent1[index1];
        Package crossover2 = parent1[index1 + 1];

        int restIndex = 0;
        for(int i = 0; i < parent2.length; i++){
            if(i >= index1 && i < index2){
                child[i] = parent1[i];
            }else{
                while(crossover1 == parent2[restIndex] || crossover2 == parent2[restIndex]){
                    restIndex++;
                }
                child[i] = parent2[restIndex];
                restIndex++;
            }
        }
        return child;
    }

    private static Package[] randomMutate(Package[] packages) {
        if (random.nextInt(100) < 20 ) {
            int randomIndex1 = random.nextInt(packages.length);
            int randomIndex2 = random.nextInt(packages.length);

            Package temp = packages[randomIndex1];
            packages[randomIndex1] = packages[randomIndex2];
            packages[randomIndex2] = temp;
        }
        return packages;
    }

    public static void solve(Package[] packages) {
        Package[][] population = new Package[populationSize + childrenSize][packages.length]; // population of paths
        Package[] bestPath = new Package[packages.length];
        double[] costs;
        double bestCost = Package.getAproxCost(packages);

        int generation = 0;
        int genSinceImprovement = 0;

        population = Arrays.stream(population).map(p -> shuffle(packages.clone())).toArray(Package[][]::new);
        costs = Arrays.stream(population).map(Package::getAproxCost).mapToDouble(Double::doubleValue).toArray();

        while (genSinceImprovement < 100) {

            Package[][] finalPopulation = population;

            Package[][] tempA = Arrays.stream(new Package[childrenSize])
                    .map(p -> randomMutate(crossover(finalPopulation[random.nextInt(populationSize)], finalPopulation[random.nextInt(populationSize)])))
                    .toArray(Package[][]::new);
            System.arraycopy(tempA, 0, population, populationSize, childrenSize);

            double[] tempC = Arrays.stream(tempA).map(Package::getAproxCost).mapToDouble(Double::doubleValue).toArray();
            System.arraycopy(tempC, 0, costs, populationSize, childrenSize);

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
                genSinceImprovement = 0;
            } else {
                genSinceImprovement++;
            }

            generation++;
        }

        System.out.println("Generation: " + generation);
        System.out.println("Best path: " + Arrays.toString(bestPath));
        System.out.println("Best Cost: " + Package.getCost(bestPath));

    }

}
