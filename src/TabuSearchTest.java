import org.junit.Test;

import java.io.IOException;

public class TabuSearchTest {

    @Test
    public void testTabuSearch() throws IOException {
        long time = System.currentTimeMillis();
        TabuSearch.solve(Main.generatePackages(500,100,100));
        System.out.println("Execution time: " + (System.currentTimeMillis() - time) + "ms");
    }
}
