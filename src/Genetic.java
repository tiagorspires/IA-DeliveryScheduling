import Packages.Package;

import java.util.*;
import java.util.stream.IntStream;

public class Genetic {
    private static final SplittableRandom random = new SplittableRandom();
    public static int populationSize = 100;
    public static int numGenerations = 1_000;
    public static double mutationProb = 0.8;
    public static int childrenSize = 100;
    public static int crossoverMethod = 1;
    public static String selectionMethod = "Best Fitness";

    public static void solveWithGeneticMenu(Scanner scanner) {
        int option = 0;
        while (option != 8) {
            try {
                System.out.println("Solve with genetic algorithm\n");
                System.out.println("Current configuration:");
                System.out.println("Population size: " + populationSize);
                System.out.println("Children size: " + childrenSize);
                System.out.println("Number of unchanged generations: " + numGenerations);
                System.out.println("Mutation Probability: " + mutationProb + "\n");
                System.out.println("Crossover method: " + crossoverMethod);
                System.out.println("Selection method: " + selectionMethod + "\n");

                System.out.println("1. Change population size");
                System.out.println("2. Change number of unchanged generations");
                System.out.println("3. Change number of children");
                System.out.println("4. Change mutation Probability [0-1]"); // Not sure if this option is to be implemented
                System.out.println("5. Change crossover method");
                System.out.println("6. Change selection method");
                System.out.println("7. Solve");
                System.out.println("8. Back");
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
                            System.out.println("Children size: ");
                            childrenSize = scanner.nextInt();
                            if (childrenSize <= 0) {
                                System.out.println("The children size must be greater than 0");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 4:
                        while (true) {
                            System.out.println("Mutation Probability [0-1]: ");
                            mutationProb = scanner.nextDouble();
                            if (mutationProb < 0 || mutationProb > 1) {
                                System.out.println("The mutation probability must be between 0 and 1");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 5:
                        while (true) {
                            System.out.println("Crossover method 1 or 2: ");
                            crossoverMethod = scanner.nextInt();
                            if (crossoverMethod != 1 && crossoverMethod != 2) {
                                System.out.println("The crossover method must be 1 or 2");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 6:
                        while (true) {
                            System.out.println("Selection method:");
                            System.out.println("1. Roulette");
                            System.out.println("2. Tournament");
                            System.out.println("3. Best Fitness");
                            int selectionMethodOption = scanner.nextInt();
                            if (!selectionMethod.equals("Roulette") && !selectionMethod.equals("Tournament") && !selectionMethod.equals("Best Fitness")) {
                                System.out.println("Invalid selection method");
                                continue;
                            }
                            switch (selectionMethodOption) {
                                case 1:
                                    selectionMethod = "Roulette";
                                    break;
                                case 2:
                                    selectionMethod = "Tournament";
                                    break;
                                case 3:
                                    selectionMethod = "Best Fitness";
                                    break;
                            }
                            break;
                        }
                        break;
                    case 7:
                        long startTime = System.currentTimeMillis();
                        Package[] packages = solve(Main.packages.clone());
                        long endTime = System.currentTimeMillis();
                        System.out.println("Improved cost from " + Package.getCost(Main.packages) + " to " + Package.getCost(packages));
                        System.out.println("Execution time: " + (endTime - startTime) + "ms");
                        System.out.println("Do you want to save an image of the path? [y/n]");
                        if (scanner.next().equals("y")) {
                            Main.GenerateImage(packages, "Genetic");
                        }
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
        System.arraycopy(parent1, index1, child, index1, index2 - index1);

        for (int i = 0; i < index1; i++) {
            while(crossover1 == parent2[restIndex] || crossover2 == parent2[restIndex]){
                restIndex++;
            }
            child[i] = parent2[restIndex];
            restIndex++;
        }

        for (int i = index2; i < parent1.length; i++) {
            while(crossover1 == parent2[restIndex] || crossover2 == parent2[restIndex]){
                restIndex++;
            }
            child[i] = parent2[restIndex];
            restIndex++;
        }
        return child;
    }

    public static Package[] crossover1(Package[] parent1, Package[] parent2) {
        Package[] child = parent1.clone();
        int index = random.nextInt(parent1.length - 2);

        for (int i = 0;i < index; i++) {
            int index2 = findIndex(parent1, parent2[i]);
            Package temp = child[i];
            child[index2] = parent2[i];
            child[i] = temp;
        }

        return child;
    }

    private static <T> int findIndex(T[] array, T target) {
        return List.of(array).indexOf(target);

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

    private static int rouletteSelect(Package[][] population, double[] costs) {
        //calculate the sum of the costs
        double max = Arrays.stream(costs).max().getAsDouble();
        double sum = Arrays.stream(costs).map( c -> max - c).sum();

        //calculate the probability of each path
        double[] probabilities = Arrays.stream(costs).map(c -> (max - c) / sum).toArray();

        //calculate the cumulative probability
        for (int i = 1; i < probabilities.length; i++) {
            probabilities[i] += probabilities[i - 1];
        }

        //select the population
        Package[][] newPopulation = new Package[populationSize][];
        double[] newCosts = new double[populationSize];
        IntStream
            .range(0, populationSize)
            .parallel()
            .forEach(i -> {
                double randomValue = random.nextDouble();
                // with binary search
                for (int j = 0; j < probabilities.length; j++) {
                    if (randomValue <= probabilities[j]) {
                        newPopulation[i] = population[j];
                        newCosts[i] = costs[j];
                        break;
                    }
                }
            });

        System.arraycopy(newPopulation, 0, population, 0, populationSize);
        System.arraycopy(newCosts, 0, costs, 0, populationSize);

        //return the index of the best cost
        int index = 0;
        double min = newCosts[0];
        for (int i = 1; i < newCosts.length; i++) {
            if (newCosts[i] < min) {
                min = newCosts[i];
                index = i;
            }
        }

        return index;
    }

    private static int tournamentSelect(Package[][] population, double[] costs) {
        Package[][] newPopulation = new Package[populationSize][];
        double[] newCosts = new double[populationSize];
        IntStream
            .range(0, populationSize)
            .parallel()
            .forEach(i -> {
                int randomIndex1 = random.nextInt(population.length);
                int randomIndex2 = random.nextInt(population.length);
                if (costs[randomIndex1] < costs[randomIndex2]) {
                    newPopulation[i] = population[randomIndex1];
                    newCosts[i] = costs[randomIndex1];
                } else {
                    newPopulation[i] = population[randomIndex2];
                    newCosts[i] = costs[randomIndex2];
                }
            });

        System.arraycopy(newPopulation, 0, population, 0, populationSize);
        System.arraycopy(newCosts, 0, costs, 0, populationSize);

        //return the index of the best cost
        int index = 0;
        double min = newCosts[0];
        for (int i = 1; i < newCosts.length; i++) {
            if (newCosts[i] < min) {
                min = newCosts[i];
                index = i;
            }
        }

        return index;
    }

    private static int bestFitnessSelect(Package[][] population, double[] costs) {
        //sort based on the costs array
        Integer[] indexes = IntStream.range(0, population.length).boxed().toArray(Integer[]::new);
        Arrays.sort(indexes, Comparator.comparingDouble(a -> costs[a]));

        Package[][] newPopulation = Arrays.stream(indexes).parallel().map(i -> population[i]).toArray(Package[][]::new);
        double[] newCosts = Arrays.stream(indexes).parallel().mapToDouble(i -> costs[i]).toArray();

        System.arraycopy(newPopulation, 0, population, 0, populationSize);
        System.arraycopy(newCosts, 0, costs, 0, populationSize);

        return 0;
    }

    public static Package[] solve(Package[] packages) {
        Package[][] population = new Package[populationSize + childrenSize][packages.length]; // population of paths
        Package[] bestPath = packages.clone();
        double[] costs;
        double bestCost = Package.getAproxCost(packages);

        population = Arrays.stream(population).parallel().map(p -> shuffle(packages.clone())).toArray(Package[][]::new);
        costs = Arrays.stream(population).parallel().map(Package::getAproxCost).mapToDouble(Double::doubleValue).toArray();

        int lastImprovement = 0;

        while (lastImprovement < numGenerations) {

             Package[][] finalPopulation = population;
             if (crossoverMethod == 1){
                 Package[][] tempA = Arrays.stream(population).parallel().map(parent -> randomMutate(crossover(finalPopulation[random.nextInt(populationSize)], finalPopulation[random.nextInt(populationSize)]))).toArray(Package[][]::new);
                 System.arraycopy(tempA, 0, population, populationSize, childrenSize);
                 double[] tempC = Arrays.stream(tempA).parallel().map(Package::getAproxCost).mapToDouble(Double::doubleValue).toArray();
                 System.arraycopy(tempC, 0, costs, populationSize, childrenSize);
             }else {
                 Package[][] tempA = Arrays.stream(population).parallel().map(parent -> randomMutate(crossover1(finalPopulation[random.nextInt(populationSize)], finalPopulation[random.nextInt(populationSize)]))).toArray(Package[][]::new);
                 System.arraycopy(tempA, 0, population, populationSize, childrenSize);
                 double[] tempC = Arrays.stream(tempA).parallel().map(Package::getAproxCost).mapToDouble(Double::doubleValue).toArray();
                 System.arraycopy(tempC, 0, costs, populationSize, childrenSize);
             }

             int index = switch (selectionMethod) {
                 case "Roulette" -> rouletteSelect(population, costs);
                 case "Tournament" -> tournamentSelect(population, costs);
                 case "Best Fitness" -> bestFitnessSelect(population, costs);
                 default -> 0;
             };

             if (costs[index] < bestCost) {
                 bestPath = population[index].clone();
                 bestCost = costs[index];
                 lastImprovement = 0;
             }
             lastImprovement++;
         }

        return bestPath;
    }

}
