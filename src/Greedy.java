import java.util.ArrayList;
import java.util.LinkedList;

public class Greedy {

    public static double distance(Package p1, Package p2) {
        return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }

    public static double distance(Package p1, int x, int y) {
        return Math.sqrt(Math.pow(p1.getX() - x, 2) + Math.pow(p1.getY() - y, 2));
    }


    public static LinkedList<Package> solve(Package[] packages, int width, int height, int costPerKm ) {
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
            trueD[i] = Math.min(urgentPackages.get(i).getDeliveryTime(), trueD[i + 1] + (int) distance(urgentPackages.get(i), urgentPackages.get(i - 1)));
        }

        while (!fragilePackages.isEmpty()) {
            UrgentPackage p = urgentPackages.remove(0);
            boolean added = false;

            do {
                for (FragilePackage f: fragilePackages) {
                    f.setCost(
                        f.getBreakingCost() * (1 - Math.pow(1 - f.getBreakingChance(), distance(f, currentX, currentY) + currectDistance))
                            +
                        (distance(f, currentX, currentY) +  distance(f, p)) * costPerKm
                    );
                }

                fragilePackages.sort(FragilePackage::compareTo);

                for (FragilePackage f: fragilePackages) {
                    if (
                        currectDistance + distance(f, currentX, currentY) + distance(f, p) <= p.getDeliveryTime()
                            &&
                        f.getBreakingCost() < (distance(f, currentX, currentY) + currectDistance) * costPerKm
                    ) {
                        path.add(f);
                        currectDistance += distance(f, currentX, currentY);
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
            currectDistance += distance(p, currentX, currentY);
            currentX = p.getX();
            currentY = p.getY();

        }


        return path;
    }
}
