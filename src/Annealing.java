import Packages.Mutations;
import Packages.Package;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Annealing {

    public static double startTemperature = 10_000;
    public static double coolingRate = 0.999;
    public static double endTemperature = 1;
    public static int numUnchangedIterations = 1000;
    public static int collingSchedule = 1;
    public static String statsFile = "stats.csv";
    public static String pathFile = "path.csv";

    public static void solveWithAnnealingMenu(Scanner scanner) {
        int option = 0;
        while (option != 8) {
            try {
                System.out.println("Solve with simulated annealing\n");
                System.out.println("Current configuration:");
                System.out.println("Start temperature: " + startTemperature);
                System.out.println("Cooling rate: " + coolingRate);
                System.out.println("End temperature: " + endTemperature);
                System.out.println("Number of unchanged iterations: " + numUnchangedIterations);
                System.out.println("Mutation type: " + Mutations.mutationType + "\n");

                System.out.println("1. Change start temperature");
                System.out.println("2. Change end temperature");
                System.out.println("3. Change cooling rate");
                System.out.println("4. Change number of unchanged iterations");
                System.out.println("5. Change mutation type");
                System.out.println("6. Change cooling schedule");
                System.out.println("7. Solve");
                System.out.println("8. Back");
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
                            Mutations.mutationType = scanner.nextInt();
                            if (Mutations.mutationType != 1 && Mutations.mutationType != 2) {
                                System.out.println("The mutation type must be 1 or 2");
                                continue;
                            }
                            break;
                        }
                        break;
                    case 6:
                        while (true) {
                            System.out.println("Cooling schedule 1, 2 or 3: ");
                            System.out.println("1. Tk = T0 * coolingRate^k");
                            System.out.println("2. Tk = T0 / ln(k + 1)");
                            System.out.println("3. Tk = T0 * e^(-coolingRate * k)");
                            collingSchedule = scanner.nextInt();

                            if (collingSchedule == 1){
                                startTemperature = 1_000;
                                System.out.println("Updated cooling schedule to Tk = T0 * coolingRate^k");
                                System.out.println("Updated start temperature to 1_000");
                            }else if (collingSchedule == 2) {
                                startTemperature = 5;
                                System.out.println("Updated cooling schedule to Tk = T0 / ln(k + 1)");
                                System.out.println("Updated start temperature to 1_000");
                            }else if (collingSchedule == 3) {
                                startTemperature = 50;
                                System.out.println("Updated cooling schedule to Tk = T0 * e^(-coolingRate * k)");
                                System.out.println("Updated start temperature to 1_000");
                            }else {
                                System.out.println("The cooling schedule must be 1, 2 or 3");
                                continue;
                            }
                            break;
                        }
                    case 7:
                        long startTime = System.currentTimeMillis();
                        Package[] packages = solve(Main.packages.clone());
                        long endTime = System.currentTimeMillis();
                        System.out.println("Improved cost from " + Package.getCost(Main.packages) + " to " + Package.getCost(packages));
                        System.out.println("Execution time: " + (endTime - startTime) + "ms");
                        System.out.println("Do you want to save an image of the path? [y/n]");
                        if (scanner.next().equals("y")) {
                            Main.GenerateImage(packages, "Annealing");
                        }
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }
        }
    }

    public static Package[] solve(Package[] packages) {
        double temperature = startTemperature;
        double bestCost = Package.getCost(packages);
        double currentCost = bestCost;

        Package[] bestPath = packages.clone();
        Package[] currentPath = packages;

        int lastMutation = 0;
        int iter = 1;

        while (lastMutation < numUnchangedIterations) {
            Package[] newPath = currentPath.clone();
            Mutations.mutate(newPath);

            double newCost = Package.getCost(newPath);
            lastMutation++;

            temperature = switch (collingSchedule) {
                case 1 -> temperature * coolingRate;
                case 2 -> startTemperature / (Math.log(iter + 1));
                case 3 -> startTemperature * Math.exp(-coolingRate * iter);
                default -> temperature * coolingRate;
            };

            if (newCost - currentCost < 0 || Math.exp(-newCost - currentCost / temperature) > Math.random()) {
                currentPath = newPath;
                currentCost = newCost;
                lastMutation = 0;
            }

            if (currentCost < bestCost) {
                bestPath = currentPath;
                bestCost = currentCost;
            }
            iter++;
        }

        return bestPath;
    }

}