public class FragilePackage extends Package implements Comparable<FragilePackage>{

    private final double breakingChance;
    private final int breakingCost;

    private double cost = 0;
    public FragilePackage(int x, int y, double breakingChance, int breakingCost) {
        super(x, y);
        this.breakingChance = breakingChance;
        this.breakingCost = breakingCost;
    }

    public double getBreakingChance() {
        return breakingChance;
    }

    public int getBreakingCost() {
        return breakingCost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCost(int x, int y, int totalKm){
        return distance(x, y) * Main.costPerKm + breakingCost * (1 - Math.pow(1 - breakingChance, distance(x, y) + totalKm));
    }

    public double getCost(Package p, int totalKm){
        return distance(p) * Main.costPerKm + breakingCost * (1 - Math.pow(1 - breakingChance, distance(p) + totalKm));
    }

    @Override
    public int compareTo(FragilePackage o) {
        return Double.compare(this.cost, o.cost);
    }

    public String toString(){
        return "( F " + getX() + " " + getY() + " " + breakingChance + " " + breakingCost + " )";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof FragilePackage p)) return false;
        return p.getX() == getX() && p.getY() == getY() && p.breakingChance == breakingChance && p.breakingCost == breakingCost;
    }
}
