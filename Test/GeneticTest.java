import Packages.Package;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class GeneticTest {
    @Test
    public void TestShuffle() {
        Package[] packages = Main.generatePackages(500, 100, 100);

        Set<Package> a = Arrays.stream(packages).collect(Collectors.toSet());

        Genetic.shuffle(packages.clone());

        Set<Package> b = Arrays.stream(packages).collect(Collectors.toSet());

        assert(a.equals(b));

    }

    @Test
    public void TestCrossover() {
        Package[] packages = Main.generatePackages(500, 100, 100);

        for (int i = 0; i < 10; i++) {
            Package[] parent1 = Genetic.shuffle(packages);
            Package[] parent2 = Genetic.shuffle(packages);

            Package[] child = Genetic.crossover(parent1, parent2);

            Set<Package> a = Arrays.stream(parent1).collect(Collectors.toSet());

            Set<Package> b = Arrays.stream(parent2).collect(Collectors.toSet());

            Set<Package> c = Arrays.stream(child).collect(Collectors.toSet());

            assert(a.equals(b));
            assert(a.equals(c));
        }

    }
}
