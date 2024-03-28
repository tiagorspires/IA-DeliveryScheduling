import Packages.Mutations;
import Packages.Package;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.stream.IntStream;

public class PerformanceTest {

    @Test
    public void GetExecutionTime() {
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
    public void execute50AnnealingTimes() throws IOException {

        OutputStream file2 = new BufferedOutputStream(new FileOutputStream("Annealing.csv"));
        file2.write("cooling,mutation,temperature,population,time,cost\n".getBytes());

        int[] temperature;
        int[] populationSize = {100,500,1000};


        for (int cool = 1; cool <= 3; cool++) {
            Annealing.collingSchedule = cool;
            if (cool == 1) {
                temperature = new int[]{1000};
            } else if (cool == 2){
                temperature = new int[]{5, 10, 15};
            }else{
                temperature = new int[]{10, 50, 100};
            }
            for (int mutationType = 1; mutationType <= 2; mutationType++) {
                Mutations.mutationType = mutationType;
                for (int population : populationSize) {
                    for (int t : temperature) {
                        Annealing.startTemperature = t;
                        long time = System.currentTimeMillis();
                        String c = cool == 1 ? "T0 mul a^k" : cool == 2 ? "T0 div ln(k +1)" : "T0 mul e^(-a mul k)";

                        double sum = IntStream.range(0, 50)
                                .parallel()
                                .mapToDouble(i -> Package.getCost(Annealing.solve(Main.generatePackages(population, 100, 100))))
                                .sum();

                        file2.write((c + "," + mutationType + "," + t + "," + population + "," + (System.currentTimeMillis() - time) + "," + sum / 50 +"\n").getBytes());
                        file2.flush();

                        System.out.println("Average cost: " + sum / 50 + " total time: " + (System.currentTimeMillis() - time) + "ms" + " for mutation type: " + mutationType + " and temperature: " + t + " and population size: " + population + " and cooling schedule: " + cool);
                        System.out.println("------------------------------------------------------------");
                    }
                }
            }
        }

        file2.close();

    }

    @Test
    public void execute50TabuTimes() throws IOException {

        OutputStream file;
        OutputStream file2 = new BufferedOutputStream(new FileOutputStream("Tabu.csv"));
        file2.write("mutation,tenure,population,time,cost\n".getBytes());

        int[] tenure = {10000,10_0000,250_000};
        int[] populationSize = {100,500,1000};

        for (int mutationType = 1; mutationType <= 2; mutationType++) {
            TabuSearch.mutationType = mutationType;
            for (int population : populationSize) {
                for (int t : tenure) {
                    TabuSearch.tenure = t;
                    long time = System.currentTimeMillis();
                    file = new BufferedOutputStream(new FileOutputStream("TabuSearch mutation type" + mutationType + " tenure" + t + " population" + population + ".csv"));
                    OutputStream finalFile = file;
                    double sum = IntStream.range(0, 50)
                            .mapToDouble(i -> {
                                long time1 = System.currentTimeMillis();
                                double cost = Package.getCost(TabuSearch.solve(Main.generatePackages(population, 100, 100)));
                                try {
                                    finalFile.write((cost + "," + (System.currentTimeMillis() - time1) +"\n").getBytes());
                                    finalFile.flush();
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                return cost;
                            })
                            .sum();

                    finalFile.close();

                    file2.write((mutationType + "," + t + "," + population   + "," + (System.currentTimeMillis() - time) + "," + sum / 50 +"\n").getBytes());
                    file2.flush();

                    System.out.println("Average cost: " + sum / 50 + " total time: " + (System.currentTimeMillis() - time) + "ms" + " for mutation type: " + mutationType + " and tenure: " + t + " and population size: " + population);
                    System.out.println("------------------------------------------------------------");
                }
            }
        }

        file2.close();

    }

    @Test
    public void executeHillClimbing50Times() throws IOException {

        OutputStream file;
        OutputStream file2 = new BufferedOutputStream(new FileOutputStream("hillClimbing.csv"));
        file2.write("mutation,tenure,population,time,cost\n".getBytes());

        int[] populationSize = {100,500,1000};

        for (int mutationType = 1; mutationType <= 2; mutationType++) {
            Mutations.mutationType = mutationType;
            for (int population : populationSize) {

                long time = System.currentTimeMillis();
                file = new BufferedOutputStream(new FileOutputStream(" mutation type" + mutationType + " population" + population + ".csv"));
                OutputStream finalFile = file;
                double sum = IntStream.range(0, 50)
                        .mapToDouble(i -> {
                            long time1 = System.currentTimeMillis();
                            double cost = Package.getCost(HillClimbing.solve(Main.generatePackages(population, 100, 100)));
                            try {
                                finalFile.write((cost + "," + (System.currentTimeMillis() - time1) +"\n").getBytes());
                                finalFile.flush();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            return cost;
                        })
                        .sum();

                finalFile.close();

                file2.write((mutationType + "," + population  + ","  + (System.currentTimeMillis() - time) + sum / 50 + "," +"\n").getBytes());
                file2.flush();

                System.out.println("Average cost: " + sum / 50 + " total time: " + (System.currentTimeMillis() - time) + "ms" + " for mutation type: " + mutationType + " and population size: " + population);
                System.out.println("------------------------------------------------------------");

            }
        }

        file2.close();

    }
}
