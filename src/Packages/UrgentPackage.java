package Packages;

public class UrgentPackage extends Package implements Comparable<UrgentPackage>{

    private double deliveryTime;

    public UrgentPackage(int x, int y, int deliveryTime) {
        super(x, y);
        this.deliveryTime = deliveryTime;
    }

    @Override
    public int compareTo(UrgentPackage o) {
        return Double.compare(this.deliveryTime, o.deliveryTime);
    }

    public double getDeliveryTime() {
        return deliveryTime;
    }

    @Override
    public double getCost(int x, int y, int totalKm){
        if (deliveryTime > totalKm) {
            return distance(x, y) * COST_PER_KM + (deliveryTime - totalKm) * COST_PER_KM;
        }
        return distance(x, y) * COST_PER_KM;
    }

    @Override
    public double getCost(Package p, int totalKm){
        if (deliveryTime > totalKm + distance(p)){
            return distance(p) * COST_PER_KM + (deliveryTime - totalKm + distance(p)) * COST_PER_KM;
        }
        return distance(p) * COST_PER_KM;
    }

    public String toString(){
        return String.format("( U %d %d %d )", getX(), getY(), (int) deliveryTime);
    }

    public void setDeliveryTime(double i) {
        deliveryTime = i;
    }
}
