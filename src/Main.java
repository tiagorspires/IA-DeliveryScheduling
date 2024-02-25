import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static int width = 100;
    public static int height = 100;
    public static double costPerKm = 3;
    public static int urgentMaxDeliveryTime = 240;
    public static int urgentMinDeliveryTime = 100;
    public static int fragileMaxBreakingCost = 3;
    public static int fragileMinBreakingCost = 10;
    public static double fragileMaxBreakingChance = 0.01;
    public static double fragileMinBreakingChance = 0.0001;

    public static Package[] packages;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option = 0;

        while (option != 5) {
            System.out.println("Welcome to the package delivery system\n");
            System.out.println("1. Generate packages");
            System.out.println("2. Solve with greedy");
            System.out.println("3. Solve with simulated annealing");
            System.out.println("4. Solve with Hill Climbing algorithm");
            System.out.println("5. Solve with genetic algorithm");
            System.out.println("6. Exit");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    generatePackageMenu(scanner);
                    break;
                case 2:
                    if (packages == null) {
                        System.out.println("You need to generate the packages first");
                        break;
                    }
                    Greedy.solveWithGreedyMenu(scanner);
                    break;
                case 3:
                    if (packages == null) {
                        System.out.println("You need to generate the packages first");
                        break;
                    }
                    Annealing.solveWithAnnealingMenu(scanner);
                    break;
                case 4:
                    if (packages == null) {
                        System.out.println("You need to generate the packages first");
                        break;
                    }
                    HillClimbing.solveWithHillClimbingMenu(scanner);
                    break;
                case 5:
                    if (packages == null) {
                        System.out.println("You need to generate the packages first");
                        break;
                    }
                    Genetic.solveWithGeneticMenu(scanner);
                    break;
            }
        }

    }

    public static void generatePackageMenu(Scanner scanner) {
        System.out.println("Generate packages\n");
        int option = 0;
        while (option != 7) {
            System.out.println("Current configuration:");
            System.out.println("Width: " + width);
            System.out.println("Height: " + height);
            System.out.println("Cost per km: " + costPerKm);
            System.out.println("Urgent max delivery time: " + urgentMaxDeliveryTime);
            System.out.println("Urgent min delivery time: " + urgentMinDeliveryTime);
            System.out.println("Fragile max breaking cost: " + fragileMaxBreakingCost);
            System.out.println("Fragile min breaking cost: " + fragileMinBreakingCost);
            System.out.println("Fragile max breaking chance: " + fragileMaxBreakingChance);
            System.out.println("Fragile min breaking chance: " + fragileMinBreakingChance);

            System.out.println("1. Change size");
            System.out.println("2. Change cost per km");
            System.out.println("3. Change urgent delivery time bounds");
            System.out.println("4. Change fragile breaking cost bounds");
            System.out.println("5. Change fragile breaking chance bounds");
            System.out.println("6. Generate packages");
            System.out.println("7. Back");

            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Enter the new width:");
                    width = scanner.nextInt();
                    System.out.println("Enter the new height:");
                    height = scanner.nextInt();
                    break;
                case 2:
                    System.out.println("Enter the new cost per km:");
                    costPerKm = scanner.nextDouble();
                    break;
                case 3:
                    while (true) {
                        System.out.println("Enter the new urgent max delivery time:");
                        urgentMaxDeliveryTime = scanner.nextInt();
                        System.out.println("Enter the new urgent min delivery time:");
                        urgentMinDeliveryTime = scanner.nextInt();

                        if (urgentMaxDeliveryTime <= urgentMinDeliveryTime) {
                            System.out.println("The max delivery time must be greater than the min delivery time");
                            continue;
                        }

                        if (urgentMaxDeliveryTime <= 0 || urgentMinDeliveryTime <= 0) {
                            System.out.println("The delivery time must be greater than 0");
                            continue;
                        }

                        break;
                    }
                    break;
                case 4:
                    while (true) {
                        System.out.println("Enter the new fragile max breaking cost:");
                        fragileMaxBreakingCost = scanner.nextInt();
                        System.out.println("Enter the new fragile min breaking cost:");
                        fragileMinBreakingCost = scanner.nextInt();

                        if (fragileMaxBreakingCost <= fragileMinBreakingCost) {
                            System.out.println("The max breaking cost must be greater than the min breaking cost");
                            continue;
                        }

                        if (fragileMaxBreakingCost <= 0 || fragileMinBreakingChance <= 0) {
                            System.out.println("The breaking cost must be greater than 0");
                            continue;
                        }

                        break;
                    }
                    break;
                case 5:
                    while (true) {
                        System.out.println("Enter the new fragile max breaking chance:");
                        fragileMaxBreakingChance = scanner.nextDouble();
                        System.out.println("Enter the new fragile min breaking chance:");
                        fragileMinBreakingChance = scanner.nextDouble();

                        if (fragileMaxBreakingChance <= fragileMinBreakingChance) {
                            System.out.println("The max breaking chance must be greater than the min breaking chance");
                            continue;
                        }

                        if (fragileMaxBreakingChance <= 0 || fragileMaxBreakingChance >= 1) {
                            System.out.println("The max breaking chance must be between 0 and 1");
                            continue;
                        }

                        if (fragileMinBreakingChance <= 0 || fragileMinBreakingChance >= 1) {
                            System.out.println("The min breaking chance must be between 0 and 1");
                            continue;
                        }

                        break;
                    }

                    break;
                case 6:
                    while (true) {
                        System.out.println("Enter the number of packages:");
                        int n = scanner.nextInt();
                        if (n <= 0) {
                            System.out.println("The number of packages must be greater than 0");
                            continue;
                        }
                        packages = generatePackages(n, width, height);
                        break;
                    }
                    option = 7;
                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    public static double distance(Package p1, Package p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public static double distance(Package p1, int x, int y) {
        return Math.sqrt(Math.pow(p1.getX() - x, 2) + Math.pow(p1.getY() - y, 2));
    }

    public static Package[] generatePackages(int n, int width, int height) {

        //create array of packages
        Package[] packages = new Package[n];
        for (int i = 0; i < n; i++) {
            int randomType = (int) (Math.random() * 3);
            int randomX = (int) (Math.random() * width);
            int randomY = (int) (Math.random() * height);
            switch (randomType) {
                case 0:
                    packages[i] = new Package(randomX, randomY);
                    break;
                case 1:
                    int deliveryTime = (int) ((Math.random()  * (urgentMaxDeliveryTime - urgentMinDeliveryTime))  + urgentMinDeliveryTime);
                    packages[i] = new UrgentPackage(randomX, randomY, deliveryTime);
                    break;
                case 2:
                    int breakingCost = (int) ((Math.random()  * (fragileMaxBreakingCost - fragileMinBreakingCost))  + fragileMinBreakingCost);
                    double breakingChance = (Math.random()  * (fragileMaxBreakingChance - fragileMinBreakingChance))  + fragileMinBreakingChance;
                    packages[i] = new FragilePackage(randomX, randomY, breakingChance, breakingCost);
                    break;
            }
        }

        return packages;
    }
}