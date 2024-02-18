import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnealingTest {

    private static final HashSet<String> original = new HashSet<>();

    private static final Package[] packages = Main.generatePackages(500,100,100);
    @BeforeAll
    public static void setUp() {
        for (Package p : packages) {
            original.add(p.toString());
        }
    }
    @Test
    public void mutation1Test() {

        // repeat the test 100 times
        for (int i = 0; i < 100; i++) {
            Package[] p = packages.clone();

            Annealing.mutation1(p);

            // check if the original set contains the mutated packages
            Set<Package> a = Arrays.stream(p).collect(Collectors.toSet());

            for (Package p1: packages) {
                assert(a.contains(p1));
            }
        }
    }

    @Test
    public void mutation2Test() {
        for (int i = 0; i < 100; i++) {
            Package[] p = packages.clone();

            Annealing.mutation2(p);

            // check if the original set contains the mutated packages
            Set<Package> a = Arrays.stream(p).collect(Collectors.toSet());

            for (Package p1: packages) {
                assert(a.contains(p1));
            }
        }
    }

    @Test
    public void mutation3Test() {
        // repeat the test 100 times
        for (int i = 0; i < 100; i++) {
            Package[] p = packages.clone();

            Annealing.mutation3(p);

            // check if the original set contains the mutated packages
            Set<Package> a = Arrays.stream(p).collect(Collectors.toSet());

            for (Package p1: packages) {
                assert(a.contains(p1));
            }
        }
    }

    @Test
    public void SolveTest() throws IOException {
        Annealing.mutationType = 2;

        Package[] newPackages = Annealing.solve(packages);

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }
    }

}
