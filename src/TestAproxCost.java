import Packages.Package;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class TestAproxCost {

    //test if multiple package paths preserve the same order when ordered based getAproxCost as getCost
    @Test
    public void Test() throws IOException {
        List<Package[]> population = new ArrayList<>();
        Package[] p = Main.generatePackages(500, 100, 100);
        int n = 10000;
        for (int i = 0; i < n; i++) {
            population.add(Genetic.shuffle(p));
        }

        double[] costs = new double[n];
        int[] indexes = new int[n];
        double[] aproxCosts = new double[n];
        int[] aproxCostIndexes = new int[n];

        for (int i = 0; i < n; i++) {
            indexes[i] = i;
            aproxCostIndexes[i] = i;
        }

        for (int i = 0; i < n; i++) {
            costs[i] = Package.getCost(population.get(i));
            aproxCosts[i] = Package.getAproxCost(population.get(i));
        }

        //sort by cost
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
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
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
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

        FileWriter fileWriter = new FileWriter("test.csv");
        fileWriter.write("AproxCostIndex,AproxCost,Index,Cost,Ratio\n");
        Locale.setDefault(Locale.US);

        for (int i = 0; i < n; i++) {
            double AproxCost = Package.getAproxCost(population.get(aproxCostIndexes[i]));
            double Cost = Package.getCost(population.get(aproxCostIndexes[i]));
            fileWriter.write(String.format("%d,%.2f,%.2f,%f,%d\n",aproxCostIndexes[i], AproxCost, Cost, AproxCost / Cost,indexes[i]));
        }
        fileWriter.close();

        //check if they are in the same order
        for (int i = 0; i < n; i++) {
            assert(Arrays.equals(indexes, aproxCostIndexes));
        }
    }

    @Test
    public void Test2() {
        int n = 100000;
        Package[] p = Main.generatePackages(100, 100, 100);
        int count = 0;
        for (int i = 0; i < n; i++) {
            Package[] a = Genetic.shuffle(p);
            Package[] b = Genetic.shuffle(p);

            double AproxCostA = Package.getAproxCost(a);
            double AproxCostB = Package.getAproxCost(b);

            double CostA = Package.getCost(a);
            double CostB = Package.getCost(b);


            if (!(((AproxCostA < AproxCostB) && (CostA < CostB)) || ((AproxCostA > AproxCostB) && (CostA > CostB)))){
                count++;
            }

        }

        System.out.println(((double)count)/n);
        assert(count == 0);

    }


}
