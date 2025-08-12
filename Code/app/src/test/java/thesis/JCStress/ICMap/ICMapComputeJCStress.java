package thesis.JCStress.ICMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IZ_Result;

import thesis.ConcurrentHashMap.ObservableConcurrentMap;
import thesis.ConcurrentHashMap.OperationsHM.Compute;
import thesis.ConcurrentHashMap.OperationsHM.MapOperation;
import thesis.ConcurrentHashMap.OperationsHM.Put;

@JCStressTest
@Outcome(id = "100, true", expect = Expect.ACCEPTABLE, desc = "All OK (Key present, Value equal to value1 of value2, Linearizability OK)")
@Outcome(id = "200, true", expect = Expect.ACCEPTABLE, desc = "All OK (Key present, Value equal to value1 of value2, Linearizability OK)")
@Outcome(expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class ICMapComputeJCStress {
    
    private final ObservableConcurrentMap<String, Integer> map = new ObservableConcurrentMap<>();
    int putValue = 100;
    int computeValue = 200;

    // Using PutOperation to set an initial value
    // This is to ensure that the map has a value before compute operations are run
    private final MapOperation<String, Integer> put = new Put<String,Integer>("key", putValue);
    
    // Using ComputeOperation to test compute functionality
    private final MapOperation<String, Integer> compute = new Compute<String,Integer>("key", (k, v) -> computeValue);

    
    Map<String, Integer> map_seq1 = new HashMap<>();
    Map<String, Integer> map_seq2 = new HashMap<>();

    @Actor
    public void actor1(){
        // Put an initial value in the map first
        //put.run(map);
        // First compute operation
        compute.run(map);
    }

    @Actor
    public void actor2(){
        // Second compute operation
        //compute.run(map);
        // Put a value in the map
        put.run(map);
    }

    @Arbiter
    public void arbiter(IZ_Result r) {
        
        //Sequence 1 
        map_seq1.put("key", putValue);
        map_seq1.compute("key", (k, v) -> computeValue);

        //Sequence 2
        map_seq2.compute("key", (k, v) -> computeValue);
        map_seq2.put("key", putValue);

        // Check if the sequences match the map's state
        // Equal method which checks all
        // Equality of two Objects
        // Value of each key should be mapped 
        boolean seq1 = Objects.equals(map_seq1.get("key"), map.get("key"));
        boolean seq2 = Objects.equals(map_seq2.get("key"), map.get("key"));

        // Record outcomes
        // Check if the key exists
        //r.r1 = map.containsKey("key") ? 1 : 0; 
        
        // Check if the value is either value1 or value2
        if(map.get("key").equals(putValue))             r.r1 = putValue;
        else if(map.get("key").equals(computeValue))    r.r1 = computeValue;
        else                                                r.r1 = -1;

        // Assert linearizability
        // Sequential execution?
        r.r2 = (seq1 || seq2) ? true : false;
    }
}
