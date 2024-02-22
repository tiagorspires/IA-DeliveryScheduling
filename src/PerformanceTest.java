import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.concurrent.Callable;

public class PerformanceTest {
    private static final Package[] packages = Main.generatePackages(500, 100, 100);

    @BeforeAll
    public static void setUp() {
    }


    public void Test(Callable<Package[]> algo, String name) {
        double total = 0;
        double totalTime = 0;
        System.out.println("Testing " + name);


        for (int i = 0; i < 10; i++) {
            try {

                double time = System.currentTimeMillis();
                Package[] path = algo.call();

                totalTime += (System.currentTimeMillis() - time);
                total += Annealing.getCost(path);
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
        Annealing.mutationType = 2;
        Test(() -> Annealing.solve(packages), "Simulated Annealing with mutation 2");
    }
}
