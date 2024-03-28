import Packages.Mutations;
import Packages.Package;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class TestAlgorithms {

    private static final HashSet<String> original = new HashSet<>();

    private static final Package[] packages = Main.generatePackages(500,100,100);
    @BeforeAll
    public static void setUp() {
        for (Package p : packages) {
            original.add(p.toString());
        }
    }
    @Test
    public void testAnnealing() {
        Mutations.mutationType = 2;
        Annealing.numUnchangedIterations = 10;

        Package[] newPackages = Annealing.solve(packages.clone());

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }

        Mutations.mutationType = 1;

        newPackages = Annealing.solve(packages.clone());

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }
    }

    @Test
    public void testHillClimbing() {
        Mutations.mutationType = 2;
        HillClimbing.numUnchangedIterations = 10;

        Package[] newPackages = HillClimbing.solve(packages.clone());

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }

        Mutations.mutationType = 1;

        newPackages = HillClimbing.solve(packages.clone());

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }
    }

    @Test
    public void testGenetic() {
        Mutations.mutationType = 2;
        Genetic.numUnchangedGenerations = 10;

        Package[] newPackages = Genetic.solve(packages.clone());

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }

        Mutations.mutationType = 1;

        newPackages = Genetic.solve(packages.clone());

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }
    }

    @Test
    public void tabuSearch() {
        Mutations.mutationType = 2;
        TabuSearch.numUnchangedIterations = 10;

        Package[] newPackages = TabuSearch.solve(packages.clone());

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }

        Mutations.mutationType = 1;

        newPackages = TabuSearch.solve(packages.clone());

        for (Package p : newPackages) {
            assert(original.contains(p.toString()));
        }
    }

}
