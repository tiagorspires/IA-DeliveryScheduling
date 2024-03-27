import Packages.FragilePackage;
import Packages.Package;
import Packages.UrgentPackage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static int width = 100;
    public static int height = 100;
    public static int urgentMaxDeliveryTime = 240;
    public static int urgentMinDeliveryTime = 100;
    public static int fragileMaxBreakingCost = 10;
    public static int fragileMinBreakingCost = 3;
    public static double fragileMaxBreakingChance = 0.01;
    public static double fragileMinBreakingChance = 0.0001;

    public static Package[] packages;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println("Welcome to the package delivery system\n");
            displayCurrentConfiguration();

            System.out.println("1. Change configuration");
            System.out.println("2. Generate packages");
            System.out.println("3. Exit\n");

            System.out.print("Enter your option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    configureSystem(scanner);
                    break;
                case 2:
                    generatePackagesMenu(scanner);
                    if (packages != null) {
                        solvePackagesMenu(scanner);
                    }
                    break;
                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (option != 3);
    }

    public static void displayCurrentConfiguration() {
        System.out.println("Current configuration:");
        System.out.println("Width: " + width);
        System.out.println("Height: " + height);
        System.out.println("Cost per km: " + Package.COST_PER_KM);
        System.out.println("Urgent max delivery time: " + urgentMaxDeliveryTime);
        System.out.println("Urgent min delivery time: " + urgentMinDeliveryTime);
        System.out.println("Fragile max breaking cost: " + fragileMaxBreakingCost);
        System.out.println("Fragile min breaking cost: " + fragileMinBreakingCost);
        System.out.println("Fragile max breaking chance: " + fragileMaxBreakingChance);
        System.out.println("Fragile min breaking chance: " + fragileMinBreakingChance + "\n");
    }

    public static void configureSystem(Scanner scanner) {
        int option;

        do {
            displayCurrentConfiguration();

            System.out.println("1. Change width and height");
            System.out.println("2. Change cost per km");
            System.out.println("3. Change urgent delivery time bounds");
            System.out.println("4. Change fragile breaking cost bounds");
            System.out.println("5. Change fragile breaking chance bounds");
            System.out.println("6. Back\n");

            System.out.print("Enter your option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter new width: ");
                    width = scanner.nextInt();
                    System.out.print("Enter new height: ");
                    height = scanner.nextInt();
                    break;
                case 2:
                    System.out.print("Enter new cost per km: ");
                    Package.COST_PER_KM = scanner.nextDouble();
                    break;
                case 3:
                    changeDeliveryTimeBounds(scanner);
                    break;
                case 4:
                    changeBreakingCostBounds(scanner);
                    break;
                case 5:
                    changeBreakingChanceBounds(scanner);
                    break;
                case 6:
                    System.out.println("Returning to main menu.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (option != 6);
    }

    public static void changeDeliveryTimeBounds(Scanner scanner) {
        System.out.print("Enter new urgent max delivery time: ");
        urgentMaxDeliveryTime = scanner.nextInt();
        System.out.print("Enter new urgent min delivery time: ");
        urgentMinDeliveryTime = scanner.nextInt();

        if (urgentMaxDeliveryTime <= urgentMinDeliveryTime) {
            System.out.println("Error: Max delivery time must be greater than min delivery time.");
            changeDeliveryTimeBounds(scanner); // Recursive call to retry input
        }
    }

    public static void changeBreakingCostBounds(Scanner scanner) {
        System.out.print("Enter new fragile max breaking cost: ");
        fragileMaxBreakingCost = scanner.nextInt();
        System.out.print("Enter new fragile min breaking cost: ");
        fragileMinBreakingCost = scanner.nextInt();

        if (fragileMaxBreakingCost <= fragileMinBreakingCost) {
            System.out.println("Error: Max breaking cost must be greater than min breaking cost.");
            changeBreakingCostBounds(scanner); // Recursive call to retry input
        }
    }

    public static void changeBreakingChanceBounds(Scanner scanner) {
        System.out.print("Enter new fragile max breaking chance: ");
        fragileMaxBreakingChance = scanner.nextDouble();
        System.out.print("Enter new fragile min breaking chance: ");
        fragileMinBreakingChance = scanner.nextDouble();

        if (
                fragileMaxBreakingChance <= fragileMinBreakingChance ||
                fragileMaxBreakingChance <= 0 ||
                fragileMaxBreakingChance >= 1 ||
                fragileMinBreakingChance <= 0
        ) {
            System.out.println("Error: Max breaking chance must be greater than min breaking chance, and both must be between 0 and 1.");
            changeBreakingChanceBounds(scanner); // Recursive call to retry input
        }
    }

    public static void generatePackagesMenu(Scanner scanner) {
        System.out.println("Generating packages...\n");

        while (true) {
            System.out.print("Enter the number of packages: ");
            int n = scanner.nextInt();

            if (n <= 0) {
                System.out.println("Error: Number of packages must be greater than 0.");
            } else {
                packages = generatePackages(n, width, height);
                System.out.println("Packages generated successfully.\n");
                break;
            }
        }
    }

    public static void solvePackagesMenu(Scanner scanner) {
        int option;

        System.out.println("Solving packages...\n");

        do {
            System.out.println("1. Solve with greedy");
            System.out.println("2. Solve with simulated annealing");
            System.out.println("3. Solve with Hill Climbing algorithm");
            System.out.println("4. Solve with genetic algorithm");
            System.out.println("5. Solve with tabu search");
            System.out.println("6. Back\n");

            System.out.print("Enter your option: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    Greedy.solveWithGreedyMenu(scanner);
                    break;
                case 2:
                    Annealing.solveWithAnnealingMenu(scanner);
                    break;
                case 3:
                    HillClimbing.solveWithHillClimbingMenu(scanner);
                    break;
                case 4:
                    Genetic.solveWithGeneticMenu(scanner);
                    break;
                case 5:
                    TabuSearch.solveWithTabuSearchMenu(scanner);
                    break;
                case 6:
                    System.out.println("Returning to main menu.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }

        } while (option != 6);
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


    public static void GenerateImage(Package [] packages) {
        BufferedImage image = new BufferedImage(height * 10, width * 10, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        //background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, height * 10, width * 10);

        DrawPath(packages, g2d, Color.ORANGE);

        DrawPackages(packages, g2d);

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File("path.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void DrawPath(Package[] packages, Graphics2D g2d, Color color){
        g2d.setColor(color);
        for (int i = 0; i < packages.length - 1; i++) {
            g2d.drawLine(packages[i].getX() * 10 + 5, packages[i].getY() * 10 + 5, packages[i + 1].getX() * 10 + 5, packages[i + 1].getY() * 10 + 5);
            g2d.drawLine(packages[i].getX() * 10 + 4, packages[i].getY() * 10 + 4, packages[i + 1].getX() * 10 + 4, packages[i + 1].getY() * 10 + 4);
            g2d.drawLine(packages[i].getX() * 10 + 6, packages[i].getY() * 10 + 6, packages[i + 1].getX() * 10 + 6, packages[i + 1].getY() * 10 + 6);
            g2d.drawLine(packages[i].getX() * 10 + 4, packages[i].getY() * 10 + 6, packages[i + 1].getX() * 10 + 4, packages[i + 1].getY() * 10 + 6);
            g2d.drawLine(packages[i].getX() * 10 + 6, packages[i].getY() * 10 + 4, packages[i + 1].getX() * 10 + 6, packages[i + 1].getY() * 10 + 4);
            g2d.drawLine(packages[i].getX() * 10 + 5, packages[i].getY() * 10 + 4, packages[i + 1].getX() * 10 + 5, packages[i + 1].getY() * 10 + 4);
            g2d.drawLine(packages[i].getX() * 10 + 5, packages[i].getY() * 10 + 6, packages[i + 1].getX() * 10 + 5, packages[i + 1].getY() * 10 + 6);
            g2d.drawLine(packages[i].getX() * 10 + 4, packages[i].getY() * 10 + 5, packages[i + 1].getX() * 10 + 4, packages[i + 1].getY() * 10 + 5);
            g2d.drawLine(packages[i].getX() * 10 + 6, packages[i].getY() * 10 + 5, packages[i + 1].getX() * 10 + 6, packages[i + 1].getY() * 10 + 5);
        }
    }

    public static void DrawPackages(Package[] packages, Graphics2D g2d){
        double totalKm = 0;
        int previousX = packages[0].getX();
        int previousY = packages[0].getY();
        for (Package p : packages) {
            totalKm += p.distance(previousX, previousY);
            previousX = p.getX();
            previousY = p.getY();
            if (p instanceof UrgentPackage){
                if (((UrgentPackage) p).getDeliveryTime() > totalKm)
                    g2d.setColor(Color.GREEN);
                else
                    g2d.setColor(Color.RED);
            }
            else if (p instanceof FragilePackage){
                g2d.setColor(Color.BLUE);
            }
            else{
                g2d.setColor(Color.BLACK);
            }
            g2d.fillOval(p.getX() * 10, p.getY() * 10, 9, 9);
        }
    }
}
