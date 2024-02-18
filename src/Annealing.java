import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Annealing {

    public static double startTemperature = 10_000;
    public static double coolingRate = 0.999;
    public static double endTemperature = 1;
    public static int numMutationsPerIteration = 1;
    public static String statsFile = "stats.csv";
    public static String pathFile = "path.csv";

    public static void solveWithAnnealingMenu(Scanner scanner) {
        int option = 0;
        while (option != 6) {
            System.out.println("Solve with simulated annealing\n");
            System.out.println("Current configuration:");
            System.out.println("Start temperature: " + startTemperature);
            System.out.println("Cooling rate: " + coolingRate);
            System.out.println("End temperature: " + endTemperature);
            System.out.println("Number of mutations per iteration: " + numMutationsPerIteration + "\n");

            System.out.println("1. Change start temperature");
            System.out.println("2. Change end temperature");
            System.out.println("3. Change cooling rate");
            System.out.println("4. Change number of mutations per iteration");
            System.out.println("5. Solve");
            System.out.println("6. Back");
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
                        System.out.println("Number of mutations per iteration: ");
                        numMutationsPerIteration = scanner.nextInt();
                        if (numMutationsPerIteration <= 0) {
                            System.out.println("The number of mutations per iteration must be greater than 0");
                            continue;
                        }
                        break;
                    }
                    break;
                case 5:
                    try {
                        long startTime = System.currentTimeMillis();
                        solve(Main.packages);
                        long endTime = System.currentTimeMillis();
                        System.out.println("Execution time: " + (endTime - startTime) + "ms");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }

    public static double getCost(Package[] packages) {
        double espectedCost = 0;
        double currectDistance = 0;

        espectedCost += packages[0].getCost(0, 0, 0);

        for (int i = 1; i < packages.length; i++) {
            espectedCost += packages[i].getCost(packages[i - 1], (int) currectDistance);
            currectDistance += packages[i].distance(packages[i - 1]);
        }

        return espectedCost;
    }

    public static void solve(Package[] packages) throws IOException {

        BufferedWriter statsWriter;
        BufferedWriter pathWriter;
        try {
            statsWriter = new BufferedWriter(new FileWriter(statsFile));
            pathWriter = new BufferedWriter(new FileWriter(pathFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double temperature = startTemperature;
        double bestCost = getCost(packages);
        Package[] bestPath = packages.clone();

        Package[] currentPath = packages;
        double currentCost = bestCost;

        int inter = 0;

        statsWriter.write(String.join(",", "Iteration", "Best Cost", "Current Cost","Temperature"));
        statsWriter.newLine();

        while (temperature > endTemperature) {
            Package[] newPath = currentPath.clone();

            for (int i = 0; i < numMutationsPerIteration; i++){
                int randomIndex1 = (int) (Math.random() * (packages.length - 1));
                int randomIndex2 = (int) (Math.random() * (packages.length - 1));

                Package temp = newPath[randomIndex1];
                newPath[randomIndex1] = newPath[randomIndex2];
                newPath[randomIndex2] = temp;
            }

            double newCost = getCost(newPath);
            double delta = newCost - currentCost;

            if (delta < 0 || Math.exp(-delta / temperature) > Math.random()) {
                currentPath = newPath;
                currentCost = newCost;
            }

            if (newCost < bestCost) {
                bestPath = newPath;
                bestCost = newCost;
            }

            temperature *= 1 * coolingRate;
            inter++;

            statsWriter.write(String.join(",", String.valueOf(inter), String.format("%.0f",bestCost), String.format("%.0f",currentCost),String.format("%.0f",temperature)));
            statsWriter.newLine();
        }

        statsWriter.close();

    }

}
