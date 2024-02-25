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

    public String toString(){
        return "( N " + x + " " + y + " )";
    }

    // sort packages by distance from last package
    public int compareTo(Package o) {
        return Double.compare(this.distance(this.getX(), this.getY()), o.distance(this.getX(), this.getY()));
    }

}
