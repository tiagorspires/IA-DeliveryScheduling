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

    public double getCost() {
        return cost;
    }

    public double setCost(double cost) {
        return this.cost = cost;
    }

    @Override
    public int compareTo(FragilePackage o) {
        return Double.compare(this.cost, o.cost);
    }
}
