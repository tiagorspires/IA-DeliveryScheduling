import Packages.Package;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class MutationsTest {


    private static final Package[] packages = Main.generatePackages(500,100,100);

    @Test
    public void mutation1Test() {

        // repeat the test 100 times
        for (int i = 0; i < 100; i++) {
            Package[] p = packages.clone();

            Packages.Mutations.mutation1(p);

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

            Packages.Mutations.mutation2(p);

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

            Packages.Mutations.mutation3(p);

            // check if the original set contains the mutated packages
            Set<Package> a = Arrays.stream(p).collect(Collectors.toSet());

            for (Package p1: packages) {
                assert(a.contains(p1));
            }
        }
    }
}
