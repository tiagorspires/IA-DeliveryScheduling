import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

public class Greedy {
    static double cost = 0;
    static double totalKm = 0;
    static int currentX = 0;
    static int currentY = 0;
    public static void solveWithGreedyMenu(Scanner scanner) {
        System.out.println("Solve with greedy\n");
        System.out.println("1. Greedy 1");
        System.out.println("2. Greedy 2");
        System.out.println("3. Greedy 3");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                greedy1();
                break;
            case 2:
                //greedy2();
                break;
            case 3:
                //greedy3();
                break;
        }
    }
    public static double distance(Package p, int x, int y) {
        return Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2));
    }
    public static LinkedList<Package> greedy1() {
        LinkedList<Package> solution = new LinkedList<>();
        LinkedList<Package> packages = new LinkedList<>();
        Collections.addAll(packages, Main.packages);
        ArrayList<UrgentPackage> urgentPackages = new ArrayList<>();
        ArrayList<FragilePackage> fragilePackages = new ArrayList<>();
        ArrayList<Package> normalPackages = new ArrayList<>();
        for (Package p : packages) {
            if (p instanceof UrgentPackage) {
                urgentPackages.add((UrgentPackage) p);
            } else if (p instanceof FragilePackage) {
                fragilePackages.add((FragilePackage) p);
            }else  {
                normalPackages.add(p);
            }
        }
        urgentPackages.sort(UrgentPackage::compareTo);

        for(UrgentPackage p: urgentPackages){
            if (p.getDeliveryTime() <= distance(p, currentX, currentY) ) {
                cost += Main.costPerKm * distance(p, currentX, currentY);
            } else {
                cost += Main.costPerKm * distance(p, currentX, currentY) + 2*Main.costPerKm * (p.getDeliveryTime() - distance(p, currentX, currentY));
            }
            for(UrgentPackage p1: urgentPackages){
                p1.setDeliveryTime(p1.getDeliveryTime() - distance(p, currentX, currentY));
            }
            currentX = p.getX();
            currentY = p.getY();
            totalKm += distance(p, currentX, currentY);
            solution.add(p);
            urgentPackages.remove(p);
        }

        double p_damage;
        fragilePackages.sort(FragilePackage::compareTo);

        for(FragilePackage p: fragilePackages){
            p_damage = p.getBreakingCost() * (1 - Math.pow(1 - p.getBreakingChance(), distance(p, currentX, currentY) + totalKm));
            if (p_damage > Math.random() ) {
                cost += Main.costPerKm * distance(p, currentX, currentY) + p.getBreakingCost();
            } else {
                cost += Main.costPerKm * distance(p, currentX, currentY);
            }
            solution.add(p);
            totalKm += distance(p, currentX, currentY);
            currentX = p.getX();
            currentY = p.getY();
            fragilePackages.remove(p);
        }
        normalPackages.sort(Package::compareTo);
        while (!normalPackages.isEmpty()) {
            cost += Main.costPerKm * distance(normalPackages.get(0), currentX, currentY);
            totalKm += distance(normalPackages.get(0), currentX, currentY);
            currentX = normalPackages.get(0).getX();
            currentY = normalPackages.get(0).getY();
            Package p = normalPackages.remove(0);
            solution.add(p);
        }
        System.out.println("Greedy 1 cost: " + cost);

        return solution;
    }

}
