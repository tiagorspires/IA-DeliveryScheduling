import Packages.Mutations;
import Packages.Package;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HillClimbing {
    public static int mutationType = 1;
    public static int numUnchangedIterations = 1000;
    public static String statsFile = "stats.csv";
    public static String pathFile = "path.csv";

    public static void solveWithHillClimbingMenu(Scanner scanner) {
        int option = 0;
        while (option != 4) {
            try {
                System.out.println("Solve with Hill Climbing\n");
                System.out.println("Current configuration:");
                System.out.println("Number of unchanged iterations: " + numUnchangedIterations);
                System.out.println("Mutation type: " + mutationType + "\n");

                System.out.println("1. Change number of unchanged iterations");
                System.out.println("2. Change mutation type");
                System.out.println("3. Solve");
                System.out.println("4. Back");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
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
                    case 2:
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
                    case 3:
                        long startTime = System.currentTimeMillis();
                        Package[] packages = solve(Main.packages.clone());
                        long endTime = System.currentTimeMillis();
                        System.out.println("Improved cost from " + Package.getCost(Main.packages) + " to " + Package.getCost(packages));
                        System.out.println("Execution time: " + (endTime - startTime) + "ms");
                        System.out.println("Do you want to save an image of the path? [y/n]");
                        if (scanner.next().equals("y")) {
                            Main.GenerateImage(packages);
                        }
                        break;
                }
            }catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }
        }
    }

    public static Package[] solve(Package[] packages) {
        Package[] bestPath = packages.clone();
        Package[] currentPath = packages;

        double bestCost = Package.getCost(packages);
        double currentCost = bestCost;

        int maxLastMutation = 0;
        int lastMutation = 0;

        while (lastMutation < numUnchangedIterations) {

            Package[] newPath = currentPath.clone();
            Mutations.mutate(newPath);

            double newCost = Package.getCost(newPath);
            lastMutation++;
            if (newCost - currentCost < 0 ) {
                currentPath = newPath;
                currentCost = newCost;
                lastMutation = 0;
            }

            if (newCost < bestCost) {
                bestCost = newCost;
                bestPath = newPath.clone();
            }

            maxLastMutation = Math.max(lastMutation, maxLastMutation);
        }

        return bestPath;
    }

}
