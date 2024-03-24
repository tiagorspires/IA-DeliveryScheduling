import Packages.Mutations;
import Packages.Package;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class PerformanceTest {
    private static final Package[] packages = Main.generatePackages(500, 100, 100);

    public void Test(Callable<Package[]> algo, String name) {
        double total = 0;
        double totalTime = 0;
        System.out.println("Testing " + name);


        for (int i = 0; i < 10; i++) {
            try {

                double time = System.currentTimeMillis();
                Package[] path = algo.call();

                totalTime += (System.currentTimeMillis() - time);
                total += Package.getCost(path);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("Average cost: " + total / 10 + "\n");
        System.out.println("Average time: " + totalTime / 10 + "ms\n");
    }

    @Test
    public void TestSolve() {
        Test(() -> HillClimbing.solve(packages), "Hill Climbing");
        HillClimbing.mutationType = 2;
        Test(() -> HillClimbing.solve(packages), "Hill Climbing with mutation 2");

        Test(() -> Annealing.solve(packages), "Simulated Annealing");
        Mutations.mutationType = 2;
        Test(() -> Annealing.solve(packages), "Simulated Annealing with mutation 2");
    }

    @Test
    public void execute50times() throws IOException {
        int[] packageNum = {100,500,1000};
        int[] tenure = {10000,10_0000,250_000};

        TabuSearch.mutationType = 2;
        for (int populationSize : packageNum) {
            for (int t : tenure) {

                TabuSearch.tenure = t;
                long startTime = System.currentTimeMillis();

                TabuSearch.solve(Main.generatePackages(populationSize, 100, 100));

                System.out.println("Execution time: " + (System.currentTimeMillis() - startTime) + "ms" + " for population size: " + populationSize + " and tenure: " + t);
                System.out.println("------------------------------------------------------------");
            }
        }
    }

    @Test
    public void TestGenetic() {
        long time = System.currentTimeMillis();

        double sum = IntStream.range(0, 50)
                .parallel()
                .mapToDouble(i -> Package.getCost(Greedy.greedy1(Main.generatePackages(1000,100,100)).toArray(Package[]::new)))
                .sum();
        System.out.println("Average cost: " + sum / 50 + " total time: " + (System.currentTimeMillis() - time) + "ms\n");
        System.out.println("------------------------------------------------------------");
    }

}
