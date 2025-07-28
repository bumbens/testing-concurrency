package thesis;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.II_Result;

@JCStressTest
@Outcome(id = "0, 1", expect = Expect.FORBIDDEN, desc = "Put happened, remove did't see or remove the key; might be timing-related")
@Outcome(id = "1, 0", expect = Expect.FORBIDDEN, desc = "Remove ran before put (didn't see anything), put executed after remove.")
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE, desc = "Remove didn't work or put overwrote the remove.")
@Outcome(id = "0, 0", expect = Expect.ACCEPTABLE, desc = "Put never happened or remove didn't see the key.")
@State
public class ICMapJCStressTest {

    private final InstrumentedConcurrentMap<String, Integer> map = new InstrumentedConcurrentMap<>();
    Map<String, Integer> map_seq1 = new HashMap<>();
    Map<String, Integer> map_seq2 = new HashMap<>();

    private final MapOperation<String, Integer> put = new PutOperation<>("key", 1);
    private final MapOperation<String, Integer> remove = new RemoveOperation<>("key");


    @Actor
    public void actor1(){
        put.run(map);

    }

    @Actor
    public void actor2(){
        remove.run(map);
    }

    @Arbiter
    public void arbiter(II_Result r) {

    // Sequence 1
    map_seq1.put("key", 1);
    map_seq1.remove("key");

    //Sequence 2
    map_seq2.remove("key");
    map_seq2.put("key", 1);


    // Check if the sequences match the map's state
    boolean seq1 = Objects.equals(map_seq1.get("key"), map.get("key")); //Objects.equals to handle nulls
    boolean seq2 = Objects.equals(map_seq2.get("key"), map.get("key")); //Objects.equals to handle nulls

    // Record outcomes
    // Check if the key exists
    r.r1 = map.containsKey("key") ? 1 : 0;
    // Check if the value is present
    // If the key exists, it should be 1, otherwise 0
    r.r2 = map.get("key") != null ? 1 : 0;

    // Assert linearizability
    assert seq1 || seq2 : "Result not linearizable";
}
}
