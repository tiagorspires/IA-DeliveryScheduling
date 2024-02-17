public class UrgentPackage extends Package implements Comparable<UrgentPackage>{

    private final int deliveryTime;

    public UrgentPackage(int x, int y, int deliveryTime) {
        super(x, y);
        this.deliveryTime = deliveryTime;
    }

    @Override
    public int compareTo(UrgentPackage o) {
        return Integer.compare(this.deliveryTime, o.deliveryTime);
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public double getCost(int x, int y, int totalKm){
        if (deliveryTime > totalKm) {
            return distance(x, y) * Main.costPerKm + (deliveryTime - totalKm) * Main.costPerKm;
        }
        return distance(x, y) * Main.costPerKm;
    }

    public double getCost(Package p, int totalKm){
        if (deliveryTime > totalKm + distance(p)){
            return distance(p) * Main.costPerKm + (deliveryTime - totalKm + distance(p)) * Main.costPerKm;
        }
        return distance(p) * Main.costPerKm;
    }
}
