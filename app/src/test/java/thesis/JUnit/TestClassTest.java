package thesis.JUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import thesis.ValidPermutations;
import thesis.CaseStudies.ArrayList.ArrayList_Example;
import thesis.Collections.Operations;
import thesis.HTML_Reports.ToFileReport;

public class TestClassTest {

    private ArrayList_Example<String> map;
    String addValue = "test";
    String removeValue = "test";
    
    @BeforeEach
    public void setUp(){
        //map = new ArrayList_Example<String>();
    }

    
    @Test
    public void testAdd(){
        
        map.add(addValue);
        assertEquals(addValue, map.get(0));
        
    }

    @Test
    public void testRemove(){
        map.add(addValue);
        map.remove(removeValue);
        assertEquals(0, map.size());

    }


    @RepeatedTest(10)
    public void testPermutations() throws InterruptedException{
        String add1 = "v1";
        String add2 = "v2";
        String remove = "v2";
        List<String> allOutcomes = new ArrayList<>();
        CountDownLatch latch;
        int iter = 0;
        int iterations = 10;
                

        final Set<List<String>> Expected = ValidPermutations.permutations(
            List.of(
                Operations.add("v1"), 
                Operations.add("v2"),
                Operations.remove("v2"),
                Operations.snapshot()
            ), 
            ArrayList::new);

            

            for (int i = 0; i < iterations; i++) {

            map = new ArrayList_Example<String>();
            latch = new CountDownLatch(1);

            Thread t1 = new Thread(() -> {
                
                map.add(add2);
            
            });

            Thread t2 = new Thread(() -> {
                map.add(add1);
                
            });

            Thread t3 = new Thread(() -> {
                
                map.remove(remove);
            });



            t1.start(); t2.start(); t3.start(); 
            latch.countDown();
            
            

            List<String> snapshot = map.getSnapshot();
            boolean passed = Expected.contains(snapshot);
            String path = "TestClassReport" + iter + ".html";
            if(passed) ToFileReport.saveToFile(path, null, map.getHistory());
            allOutcomes.add(snapshot.toString());
            assertTrue(
                        Expected.contains(map.getSnapshot()),
                        () -> 
                        "Unexpected snapshot: " + map + " | expected any of " + Expected
                        
                    );
            }
            
            
            

        }


}

