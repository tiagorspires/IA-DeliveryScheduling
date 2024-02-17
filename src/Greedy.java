import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Greedy {


    public static void solveWithGreedyMenu(Scanner scanner) {
        int option = 0;
        while (option != 2) {
            System.out.println("Solve with greedy\n");
            System.out.println("1. Solve");
            System.out.println("2. Back");
            option = scanner.nextInt();
            if (option == 1) {
                solve(Main.packages);
            }
        }
    }

    public static LinkedList<Package> solve(Package[] packages) {
        //separate the packages by type
        ArrayList<UrgentPackage> urgentPackages = new ArrayList<>();
        ArrayList<FragilePackage> fragilePackages = new ArrayList<>();
        ArrayList<Package> normalPackages = new ArrayList<>();

        for (Package p : packages) {
            if (p instanceof UrgentPackage) {
                urgentPackages.add((UrgentPackage) p);
            } else if (p instanceof FragilePackage) {
                fragilePackages.add((FragilePackage) p);
            } else {
                normalPackages.add(p);
            }
        }

        //add the urgent packages to the path
        urgentPackages.sort(UrgentPackage::compareTo);
        LinkedList<Package> path = new LinkedList<>();
        int espectedCost = 0;
        double currectDistance = 0;
        int currentX = 0;
        int currentY = 0;

        int[] trueD = new int[urgentPackages.size()];
        for (int i = urgentPackages.size() - 1; i >= 1; i--) {
            trueD[i] = Math.min(urgentPackages.get(i).getDeliveryTime(), trueD[i + 1] + (int) Main.distance(urgentPackages.get(i), urgentPackages.get(i - 1)));
        }

        while (!fragilePackages.isEmpty()) {
            UrgentPackage p = urgentPackages.remove(0);
            boolean added = false;

            do {
                for (FragilePackage f: fragilePackages) {
                    f.setCost(
                        f.getBreakingCost() * (1 - Math.pow(1 - f.getBreakingChance(), Main.distance(f, currentX, currentY) + currectDistance))
                            +
                        (Main.distance(f, currentX, currentY) +  Main.distance(f, p)) * 3
                    );
                }

                fragilePackages.sort(FragilePackage::compareTo);

                for (FragilePackage f: fragilePackages) {
                    if (
                        currectDistance + Main.distance(f, currentX, currentY) + Main.distance(f, p) <= p.getDeliveryTime()
                            &&
                        f.getBreakingCost() < (Main.distance(f, currentX, currentY) + currectDistance) * 3
                    ) {
                        path.add(f);
                        currectDistance += Main.distance(f, currentX, currentY);
                        currentX = f.getX();
                        currentY = f.getY();
                        espectedCost += f.getBreakingCost();
                        fragilePackages.remove(f);
                        added = true;
                        break;
                    }
                }
            } while (added);

            path.add(p);
            currectDistance += Main.distance(p, currentX, currentY);
            currentX = p.getX();
            currentY = p.getY();

        }


        return path;
    }
}
