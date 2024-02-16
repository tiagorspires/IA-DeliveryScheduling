//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

    }

    public static Package[] generatePackages(int n, int width, int height) {

        //create array of packages
        Package[] packages = new Package[n];
        for (int i = 0; i < n; i++) {
            int randomType = (int) (Math.random() * 3);
            //random x and y
            int randomX = (int) (Math.random() * width);
            int randomY = (int) (Math.random() * height);
            switch (randomType) {
                case 0:
                    packages[i] = new Package(randomX, randomY);
                    break;
                case 1:
                     // (random * (up - down)) + down
                    int min = 100;
                    int max = 240;
                    packages[i] = new UrgentPackage(randomX, randomY, (int) (Math.random() * (max - min) + min ));
                    break;
                case 2:
                    double minP = 0.0001;
                    double maxP = 0.01;
                    int minC = 3;
                    int maxC = 10;

                    packages[i] = new FragilePackage(randomX, randomY,((Math.random()  * (maxP - minP))  + minP), (int) (Math.random() * (maxC - minC) + minC));
                    break;
            }
        }

        return packages;
    }
}