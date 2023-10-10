import org.junit.Test;
import org.lab.SimplexMethod;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class SimplexMethodTest {

    SimplexMethod simplexMethod = new SimplexMethod();

    @Test
    public void simplexMethodTest1(){
        double [][] table = {
                {4, 1, 1, 0, 8},
                {-1, 1, 0, 1, 3},
                {-3, -4, 0, 0, 0}};
        Map<String, Double> results = new HashMap<>();
        results.put("x1", 1.);
        results.put("x2", 4.);
        results.put("y1", 0.);
        results.put("y2", 0.);
        results.put("F", 19.);

        assertEquals(results, simplexMethod.doMethod(table, 2));
    }
}
