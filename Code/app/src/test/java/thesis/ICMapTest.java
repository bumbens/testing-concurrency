package thesis;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


//what kind of bugs could I produce; what kind of changes inside the code could be made to provoke bugs; how to create concurrency bugs in java code 

//false-positive  and false-negative tests

//visibility bugs, locks, synchronizations, how to detect 


public class ICMapTest {
    
    private InstrumentedConcurrentMap<String, Integer> map;

    @Before
    public void setUp() {
        map = new InstrumentedConcurrentMap<>();    
    }

    @Test
    public void testPutOp() {
        PutOperation<String, Integer> putOp = new PutOperation<>("key1", 1);
        putOp.run(map);
        assert map.get("key1").equals(1);
    }

    @Test
    public void testGetOp() {
        map.put("key2", 2);
        GetOperation<String, Integer> getOp = new GetOperation<>("key2");
        getOp.run(map);
        assert map.get("key2").equals(2);   
    }

    @Test
    public void testRemoveOp(){
        map.put("key3", 3);
        RemoveOperation<String, Integer> removeOp = new RemoveOperation<String,Integer>("key3");
        removeOp.run(map);
        assertNull(map.get("key3"));
    }

    @Test
    public void testPutOpThreads() throws InterruptedException {
        Counter counter = new Counter();
        List<String> keys = new ArrayList<>(); // List to store keys added by threads (data race vs race condition)
        Thread t1 = new Thread(() -> {
            for (int i = 1; i<= 1000; i++){
                PutOperation<String, Integer> putOp = new PutOperation<>("key" + i, i);
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
                PutOperation<String, Integer> putOp = new PutOperation<>("key" + i, i);
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
}
