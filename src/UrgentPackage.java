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
}
