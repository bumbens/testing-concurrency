package thesis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import thesis.Operations.Get;
import thesis.Operations.Put;
import thesis.Operations.Remove;


//what kind of bugs could I produce; what kind of changes inside the code could be made to provoke bugs; how to create concurrency bugs in java code 

//false-positive  and false-negative tests

//visibility bugs, locks, synchronizations, how to detect 


public class ICMapTest {
    
    private InstrumentedConcurrentMap<String, Integer> map;

    @BeforeEach
    public void setUp() {
        map = new InstrumentedConcurrentMap<>();    
    }

    @Test
    public void testPutOp() {
        Put<String, Integer> putOp = new Put<>("key1", 1);
        putOp.run(map);
        assert map.get("key1").equals(1);
    }

    @Test
    public void testGetOp() {
        map.put("key2", 2);
        Get<String, Integer> getOp = new Get<>("key2");
        getOp.run(map);
        assert map.get("key2").equals(2);   
    }

    @Test
    public void testRemoveOp(){
        map.put("key3", 3);
        Remove<String, Integer> removeOp = new Remove<String,Integer>("key3");
        removeOp.run(map);
        assertNull(map.get("key3"));
    }

    @Test
    public void testPutOpThreads() throws InterruptedException {
        Counter counter = new Counter();
        List<String> keys = new ArrayList<>(); // List to store keys added by threads (data race vs race condition)
        Thread t1 = new Thread(() -> {
            for (int i = 1; i<= 1000; i++){
                Put<String, Integer> putOp = new Put<>("key" + i, i);
                putOp.run(map); // Run the put operation
                counter.increment(); // Increment the counter
                // Synchronize access to the keys list to avoid concurrent modification issues
                synchronized(keys){
                    keys.add("key" + i);
                }
                
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1; i<= 1000; i++){
                Put<String, Integer> putOp = new Put<>("key" + i, i);
                putOp.run(map);
                counter.increment();
                synchronized(keys) {
                    keys.add("key" + i);
                }
                
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        for (int i = 1; i <= 1000; i++) {
            assertEquals(Integer.valueOf(i), map.get("key" + i)); // Check if each key was put correctly
        }

        assertEquals(2000, counter.get()); // 1000 increments from each thread
        assertEquals(2000, keys.size()); // 1000 keys from each thread
    }

    @RepeatedTest(10000)
    public void putComputeIfAbsentTest() throws InterruptedException{

        Thread t1 = new Thread(() -> {
            //map.put("key", 100);
            map.computeIfAbsent("key", k -> 200);
        });

        Thread t2 = new Thread(() -> {
            //map.computeIfAbsent("key", k -> 200);
            map.put("key", 100);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        int actualKey = map.get("key");

        assertEquals(100, actualKey);
        assertNotEquals(200, actualKey);
    }
}
