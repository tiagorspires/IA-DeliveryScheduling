package Packages;

public class FragilePackage extends Package implements Comparable<FragilePackage>{
    private final double breakingChance;
    private final int breakingCost;
    public FragilePackage(int x, int y, double breakingChance, int breakingCost) {
        super(x, y);
        this.breakingChance = breakingChance;
        this.breakingCost = breakingCost;
    }

    @Override
    public double getCost(int x, int y, int totalKm){
        return distance(x, y) * COST_PER_KM + breakingCost * (1 - Math.pow(1 - breakingChance, distance(x, y) + totalKm));
    }

    @Override
    public double getCost(Package p, int totalKm){
        return distance(p) * COST_PER_KM + breakingCost * (1 - Math.pow(1 - breakingChance, distance(p) + totalKm));
    }

    @Override
    public int compareTo(FragilePackage o) {
        return Double.compare(this.breakingChance*this.breakingCost, o.breakingChance*o.breakingCost);
    }

    public String toString(){
        return String.format("( F %d %d %.3f %d )", getX(), getY(), breakingChance, breakingCost);
    }
}
