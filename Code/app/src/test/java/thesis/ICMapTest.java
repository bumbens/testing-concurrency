package thesis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;
import thesis.ConcurrentHashMap.ToFile;
import thesis.ConcurrentHashMap.OperationsHM.Get;
import thesis.ConcurrentHashMap.OperationsHM.OperationHM;
import thesis.ConcurrentHashMap.OperationsHM.Put;
import thesis.ConcurrentHashMap.OperationsHM.Remove;


//what kind of bugs could I produce; what kind of changes inside the code could be made to provoke bugs; how to create concurrency bugs in java code 

//false-positive  and false-negative tests

//visibility bugs, locks, synchronizations, how to detect 


public class ICMapTest {
    
    private ObservableConcurrentMap<Object, Object> map;
    Object[] outputs = {"get", "put", "remove", "compute"};

    @BeforeEach
    public void setUp() {
        map = new ObservableConcurrentMap<>();   
    }

    @Test
    public void testPutOp() {
        Put<Object, Object> putOp = new Put<>("key1", 1);
        putOp.run(map);
        assert map.get("key1").equals(1);
    }

    @Test
    public void testGetOp() {
        map.put("key2", 2);
        Get<Object, Object> getOp = new Get<>("key2");
        getOp.run(map);
        assert map.get("key2").equals(2);   
    }

    @Test
    public void testRemoveOp(){
        map.put("key3", 3);
        Remove<Object, Object> removeOp = new Remove<Object,Object>("key3");
        removeOp.run(map);
        assertNull(map.get("key3"));
    }

    @Test
    public void testPutOpThreads() throws InterruptedException {
        Counter counter = new Counter();
        List<Object> keys = new ArrayList<>(); // List to store keys added by threads (data race vs race condition)
        Thread t1 = new Thread(() -> {
            for (int i = 1; i<= 1000; i++){
                Put<Object, Object> putOp = new Put<>("key" + i, i);
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
                Put<Object, Object> putOp = new Put<>("key" + i, i);
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

    @Test
    public void putComputeIfAbsentTest() throws InterruptedException{

        int iterations = 10;

        for (int i = 0; i < iterations; i++) {
            
            Thread t1 = new Thread(() -> {
                //map.put("key", 100);
                synchronized(map){
                map.compute("key", (k, v) -> "valuePut");
            }
            }, "Thread-1");

            Thread t2 = new Thread(() -> {
                //map.computeIfAbsent("key", k -> 200);
                synchronized(map){
                map.put("key", 100);
                }
            }, "Thread-2");

            t1.start();
            t2.start();
            t1.join();
            t2.join();
        }

        Object actualValue = map.get("key");

        assertTrue(actualValue.equals(100) || actualValue.equals(200));
        //assertNotEquals(200, actualKey);

        ToFile.saveToFile(map, "result.html", actualValue);
    }

    @Test
    public void testCartesian() throws InterruptedException{
        int iterations = 5;
        final Object key1 = "key1";
        final Object key2 = "key2";
        final Object valuePut = "valuePut";
        final Object valueCompute = "valueCompute";
        List<OperationHM<Object, Object>> history;
        

        for (int i = 0; i < iterations; i++){
            // Thread t1 = new Thread(() -> { synchronized(map) { map.put(key1, valuePut); }}, "Thread-1");
            // Thread t2 = new Thread(() -> { synchronized(map) {map.put(key2, valuePut); }}, "Thread-2");
            // Thread t3 = new Thread(() -> { synchronized(map) {map.remove(key1); }}, "Thread-3");
            // Thread t4 = new Thread(() -> { synchronized(map) {map.remove(key2); }}, "Thread-4");
            // Thread t5 = new Thread(() -> { synchronized(map) {map.compute(key1, (k, v) -> valueCompute); }}, "Thread-5");
            // Thread t6 = new Thread(() -> { synchronized(map) {map.compute(key2, (k, v) -> valueCompute); }}, "Thread-6");

            Thread t1 = new Thread(() -> { map.put(key1, valuePut); }, "Thread-1");
            Thread t2 = new Thread(() -> { map.put(key2, valuePut); }, "Thread-2");
            Thread t3 = new Thread(() -> { map.remove(key1); }, "Thread-3");
            Thread t4 = new Thread(() -> { map.remove(key2); }, "Thread-4");
            Thread t5 = new Thread(() -> { map.compute(key1, (k, v) -> valueCompute); }, "Thread-5");
            Thread t6 = new Thread(() -> { map.compute(key2, (k, v) -> valueCompute); }, "Thread-6");
            
                t1.start(); t2.start(); t3.start(); t4.start(); t5.start(); t6.start();
                t1.join(); t2.join(); t3.join(); t4.join(); t5.join(); t6.join();
                }

            Object actualValue1 = map.get(key1);
            Object actualValue2 = map.get(key2);

            
            history = map.getHistory();

            
    // Check if the map is consistent
    // Check latest write for key1
    Object lastWrittenValue1 = null;
    // Track the last opCounter for key1
    long lastOpCounter1 = -1;
    // Iterate through the history to find the latest write for key1
    for (OperationHM<Object, Object> op : history) {
        // Skip operations that do not match key1
        if (!op.getKey().equals(key1)) continue;
        // Check if the operation is a write operation
        // and update the last written value if this operation has a higher opCounter
        // than the last recorded one
        // This ensures concidering only the most recent write operation
        if (op.getOpType().equals("put") || op.getOpType().equals("compute") || op.getOpType().equals("remove")) {
            if (op.getOpCounter() > lastOpCounter1) {
                lastOpCounter1 = op.getOpCounter();
                lastWrittenValue1 = op.getOutput();
            }
        }
    }

    // Check latest write for key2
    // Similar logic as above for key2
    Object lastWrittenValue2 = null;
    long lastOpCounter2 = -1;
    for (OperationHM<Object, Object> op : history) {
        if (!op.getKey().equals(key2)) continue;
        if (op.getOpType().equals("put") || op.getOpType().equals("compute") || op.getOpType().equals("remove")) {
            if (op.getOpCounter() > lastOpCounter2) {
                lastOpCounter2 = op.getOpCounter();
                lastWrittenValue2 = op.getOutput();
            }
        }
    }

    // Assertions for both keys
    assertTrue(
        Objects.equals(actualValue1, lastWrittenValue1));
    assertTrue(
        Objects.equals(actualValue2, lastWrittenValue2));

    // Save full state to file for inspection
    ToFile.saveToFile(map, "resultCartesian.html", actualValue1 + " | " + actualValue2);
        
        }
        
}
    

