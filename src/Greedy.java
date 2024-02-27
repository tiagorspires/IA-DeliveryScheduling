import Packages.FragilePackage;
import Packages.Package;
import Packages.UrgentPackage;

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
        System.out.println("1. Greedy 1"); // Urgent packages first, then fragile, then normal
        System.out.println("2. Greedy 2"); // Urgent packages first, then fragile and normal
        System.out.println("3. Greedy 3"); // Fragile packages first, then urgent, then normal
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                greedy1();
                break;
            case 2:
                greedy2();
                break;
            case 3:
                greedy3();
                break;
        }
    }
    public static int distance(Package p, int x, int y) {
        return (int) Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2));
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
            cost += p.getCost(currentX, currentY, distance(p, currentX, currentY));
            for(UrgentPackage p1: urgentPackages){
                p1.setDeliveryTime(p1.getDeliveryTime() - distance(p, currentX, currentY));
            }
            currentX = p.getX();
            currentY = p.getY();
            totalKm += distance(p, currentX, currentY);
            solution.add(p);
        }

        fragilePackages.sort(FragilePackage::compareTo);

        for(FragilePackage p: fragilePackages){
            cost+= p.getCost(currentX, currentY, distance(p, currentX, currentY));
            solution.add(p);
            totalKm += distance(p, currentX, currentY);
            currentX = p.getX();
            currentY = p.getY();
        }
        normalPackages.sort(Package::compareTo);
        while (!normalPackages.isEmpty()) {
            cost += normalPackages.get(0).getCost(currentX, currentY, distance(normalPackages.get(0), currentX, currentY));
            totalKm += distance(normalPackages.get(0), currentX, currentY);
            currentX = normalPackages.get(0).getX();
            currentY = normalPackages.get(0).getY();
            solution.add(normalPackages.get(0));
            normalPackages.remove(0);
        }
        System.out.println("Greedy 1 cost: " + cost);
        cost = 0;
        return solution;
    }

    public static LinkedList<Package> greedy2() {
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
            } else {
                normalPackages.add(p);
            }
        }

        urgentPackages.sort(UrgentPackage::compareTo);
        for (UrgentPackage u : urgentPackages) {
            cost += u.getCost(currentX, currentY, distance(u, currentX, currentY));
            totalKm += distance(u, currentX, currentY);
            currentX = u.getX();
            currentY = u.getY();
            solution.add(u);

            // Create copies of fragilePackages and normalPackages
            ArrayList<FragilePackage> fragileCopy = new ArrayList<>(fragilePackages);
            ArrayList<Package> normalCopy = new ArrayList<>(normalPackages);

            // Check if there's space for fragile packages without affecting urgent deliveries
            for (FragilePackage f : fragileCopy) {
                if (currentX != f.getX() && currentY != f.getY() &&
                        distance(f, currentX, currentY) + f.getCost(currentX, currentY, (int) totalKm) <= u.getDeliveryTime()) {
                    cost += f.getCost(currentX, currentY, (int) totalKm);
                    totalKm += distance(f, currentX, currentY);
                    currentX = f.getX();
                    currentY = f.getY();
                    solution.add(f);
                    fragilePackages.remove(f);
                }
            }

            // Check if there's space for normal packages without affecting urgent deliveries
            for (Package n : normalCopy) {
                if (currentX != n.getX() && currentY != n.getY() &&
                        distance(n, currentX, currentY) + n.getCost(currentX, currentY, (int) totalKm) <= u.getDeliveryTime()) {
                    cost += n.getCost(currentX, currentY, (int) totalKm);
                    totalKm += distance(n, currentX, currentY);
                    currentX = n.getX();
                    currentY = n.getY();
                    solution.add(n);
                    normalPackages.remove(n);
                }
            }
        }

        System.out.println("Greedy 2 cost: " + cost);
        cost = 0;
        return solution;
    }
    public static LinkedList<Package> greedy3() {
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
            } else {
                normalPackages.add(p);
            }
        }

        fragilePackages.sort(FragilePackage::compareTo);
        for (FragilePackage f : fragilePackages) {
            cost += f.getCost(currentX, currentY, distance(f, currentX, currentY));
            totalKm += distance(f, currentX, currentY);
            currentX = f.getX();
            currentY = f.getY();
            solution.add(f);
        }

        urgentPackages.sort(UrgentPackage::compareTo);
        for (UrgentPackage u : urgentPackages) {
            cost += u.getCost(currentX, currentY, distance(u, currentX, currentY));
            totalKm += distance(u, currentX, currentY);
            currentX = u.getX();
            currentY = u.getY();
            solution.add(u);
        }

        normalPackages.sort(Package::compareTo);
        for (Package n : normalPackages) {
            cost += n.getCost(currentX, currentY, distance(n, currentX, currentY));
            totalKm += distance(n, currentX, currentY);
            currentX = n.getX();
            currentY = n.getY();
            solution.add(n);
        }

        System.out.println("Greedy 3 cost: " + cost);
        return solution;
    }


}
