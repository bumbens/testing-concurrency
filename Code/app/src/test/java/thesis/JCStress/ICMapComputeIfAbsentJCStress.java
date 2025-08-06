package thesis.JCStress;

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

import thesis.ObservableConcurrentMap;
import thesis.Operations.ComputeIfAbsent;
import thesis.Operations.MapOperation;
import thesis.Operations.Put;

// Remove combinations ( only read )
// Combination read - write
// Combination read - write & write (etc...)
// read + sequence of another write methods - ?
// what is the sufficient condition to say there is not data race?
//      - black box texting, so according to the specification
// what is the condition "I've done enough testing"
//      - operation coverage is complete
//      - all possible combinations of operations are tested
//      - all possible sequences of operations are tested
//      - all possible interleavings of operations are tested
//      - no FORBIDDEN outcomes observed
//      - different hardware architectures and JVMs are tested


// EQUALITY FOR OBJECTS!!!!!!!!!!

@JCStressTest
@Outcome(id = "100, true", expect = Expect.ACCEPTABLE, desc = "All OK (Key present, Value equal to value1 of value2, Linearizability OK)")
@Outcome(expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class ICMapComputeIfAbsentJCStress {
    
    private final ObservableConcurrentMap<String, Integer> map = new ObservableConcurrentMap<>();
    final int putValue = 100;
    final int computeIfAbsentValue = 200;
    
    Map<String, Integer> map_seq1 = new HashMap<>();
    Map<String, Integer> map_seq2 = new HashMap<>();

    private final MapOperation<String, Integer> put = new Put<>("key", putValue);
    private final MapOperation<String, Integer> computeIfAbsent = new ComputeIfAbsent<String, Integer>("key", k -> computeIfAbsentValue);

    @Actor
    public void actor1() {
        // Put an initial value in the map first
        put.run(map);
    }

    @Actor
    public void actor2() {
        // computeIfAbsent operation
        computeIfAbsent.run(map);
    }

    @Arbiter
    public void arbiter(IZ_Result r) {

        //Sequence 1
        map_seq1.put("key", putValue);
        map_seq1.computeIfAbsent("key", k -> computeIfAbsentValue);

        //Sequence 2
        map_seq2.computeIfAbsent("key", k -> computeIfAbsentValue);
        map_seq2.put("key", putValue);

        // Check if the sequences match the map's state
        boolean seq1 = Objects.equals(map_seq1.get("key"), map.get("key"));
        boolean seq2 = Objects.equals(map_seq2.get("key"), map.get("key"));

        // Record outcomes
        // Check if the value is either putValue or computeIfAbsentValue
        if(map.get("key").equals(putValue))                     r.r1 = putValue;
        else if(map.get("key").equals(computeIfAbsentValue))    r.r1 = computeIfAbsentValue;
        else                                                        r.r1 = -1;


        // Assert linearizability
        r.r2 = (seq1 || seq2) ? true : false;
    }
}
