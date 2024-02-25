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
            return distance(x, y) * Main.costPerKm + (deliveryTime - totalKm) * Main.costPerKm;
        }
        return distance(x, y) * Main.costPerKm;
    }

    @Override
    public double getCost(Package p, int totalKm){
        if (deliveryTime > totalKm + distance(p)){
            return distance(p) * Main.costPerKm + (deliveryTime - totalKm + distance(p)) * Main.costPerKm;
        }
        return distance(p) * Main.costPerKm;
    }

    public String toString(){
        return "( U " + getX() + " " + getY() +  " " + deliveryTime + " )";
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof UrgentPackage p)) return false;
        return p.getX() == getX() && p.getY() == getY() && p.deliveryTime == deliveryTime;
    }

    public void setDeliveryTime(double i) {
        deliveryTime = i;
    }
}
