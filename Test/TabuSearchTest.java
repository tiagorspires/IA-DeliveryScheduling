

import Packages.Mutations;
import Packages.Package;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public class TabuSearchTest {

    @Test
    public void testTabuSearch() throws IOException {
        long time = System.currentTimeMillis();
        TabuSearch.solve(Main.generatePackages(500,100,100));
        System.out.println("Execution time: " + (System.currentTimeMillis() - time) + "ms");
    }

    @Test
    public void SolveTest() throws IOException {
        Mutations.mutationType = 2;

        Package[] packages = Main.generatePackages(500, 100, 100);
        Set<String> original = Set.of(packages).stream().map(Package::toString).collect(Collectors.toSet());

        Package[] newPackages = Annealing.solve(packages);
        Set<String> newSet = Set.of(newPackages).stream().map(Package::toString).collect(Collectors.toSet());

        assert (original.equals(newSet));

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }
    }
}
