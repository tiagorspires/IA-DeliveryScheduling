public class Package {

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
        return distance(x, y) * Main.costPerKm;
    }

    public double getCost(Package p, int totalKm){
        return distance(p) * Main.costPerKm;
    }

}
