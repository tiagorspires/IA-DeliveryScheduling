package Packages;
public class Package {
    public static double COST_PER_KM = 3;
    private final int x;
    private final int y;

    public Package(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distance(Package p){
        return Math.sqrt(Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(), 2));
    }

    public double distance(int x, int y){
        return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2));
    }

    public double getCost(int x, int y, int totalKm){
        return distance(x, y) * COST_PER_KM;
    }

    public double getCost(Package p, int totalKm){
        return distance(p) * COST_PER_KM;
    }

    public String toString(){
        return "( N " + x + " " + y + " )";
    }

    // sort packages by distance from last package
    public int compareTo(Package o) {
        return Double.compare(this.distance(this.getX(), this.getY()), o.distance(this.getX(), this.getY()));
    }

    public static double getCost(Package[] packages){
        double espectedCost = 0;
        double currectDistance = 0;

        espectedCost += packages[0].getCost(0, 0, 0);

        for (int i = 1; i < packages.length; i++) {
            espectedCost += packages[i].getCost(packages[i - 1], (int) currectDistance);
            currectDistance += packages[i].distance(packages[i - 1]);
        }

        return espectedCost;
    }

    public double AproxDistance(Package p){
        return Math.pow(this.x - p.getX(), 2) + Math.pow(this.y - p.getY(),2);
    }

    public double AproxDistance(int x, int y){
        return Math.pow(this.x - x, 2) + Math.pow(this.y - y,2);
    }

    public double getAproxCost(int x, int y, int totalKm){
        return AproxDistance(x, y) * COST_PER_KM;
    }

    public double getAproxCost(Package p, int totalKm){
        return AproxDistance(p) * COST_PER_KM;
    }

    public static double getAproxCost(Package[] packages){
        double espectedCost = 0;
        double currectDistance = 0;

        espectedCost += packages[0].getAproxCost(0, 0, 0);

        for (int i = 1; i < packages.length; i++) {
            espectedCost += packages[i].getAproxCost(packages[i - 1], (int) currectDistance);
            currectDistance += packages[i].AproxDistance(packages[i - 1]);
        }

        return espectedCost;
    }

}
