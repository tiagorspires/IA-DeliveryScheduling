import Packages.Package;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestAproxCost {



    //test if multiple package paths preserve the same order when ordered based getAproxCost as getCost
    @Test
    public void Test() {
        List<Package[]> population = new ArrayList<>();
        Package[] p = Main.generatePackages(100, 100, 100);
        for (int i = 0; i < 100; i++) {
            population.add(Genetic.shuffle(p));
        }

        List<Package[]> withCosts = new ArrayList<>();
        double[] costs = new double[100];
        int[] indexes = new int[100];
        List<Package[]> withAproxCosts = new ArrayList<>();
        double[] aproxCosts = new double[100];
        int[] aproxCostIndexes = new int[100];

        for (int i = 0; i < 100; i++) {
            indexes[i] = i;
            aproxCostIndexes[i] = i;
        }


        for (int i = 0; i < 100; i++) {
            withCosts.add(population.get(i));
            costs[i] = Package.getCost(population.get(i));

            withAproxCosts.add(population.get(i));
            aproxCosts[i] = Package.getAproxCost(population.get(i));
        }

        //sort by cost
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                if (costs[i] > costs[j]) {
                    double temp = costs[i];
                    costs[i] = costs[j];
                    costs[j] = temp;

                    int tempIndex = indexes[i];
                    indexes[i] = indexes[j];
                    indexes[j] = tempIndex;
                }
            }
        }

        //sort by aproxCost
        for (int i = 0; i < 100; i++) {
            for (int j = i + 1; j < 100; j++) {
                if (aproxCosts[i] > aproxCosts[j]) {
                    double temp = aproxCosts[i];
                    aproxCosts[i] = aproxCosts[j];
                    aproxCosts[j] = temp;

                    int tempIndex = aproxCostIndexes[i];
                    aproxCostIndexes[i] = aproxCostIndexes[j];
                    aproxCostIndexes[j] = tempIndex;
                }
            }
        }

        for (int i = 0; i < 100; i++) {
            double AproxCost = Package.getAproxCost(withAproxCosts.get(i));
            double Cost = Package.getCost(withCosts.get(i));
            System.out.printf("(%d AproxCost: %.2f)  (%d Cost: %.2f)\n",aproxCostIndexes[i], AproxCost, indexes[i], Cost);
        }

        //check if they are in the same order
        for (int i = 0; i < 100; i++) {
            assert(Arrays.equals(indexes, aproxCostIndexes));
        }
    }
}
