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
@Outcome(id = "1, 1", expect = Expect.ACCEPTABLE, desc = "All OK")
@Outcome(expect = Expect.FORBIDDEN, desc = "Forbidden output")
@State
public class ICMapComputeJCStress {
    
    private final InstrumentedConcurrentMap<String, Integer> map = new InstrumentedConcurrentMap<>();
    int v1 = 100;
    int v2 = 200;
    private final MapOperation<String, Integer> compute1 = new ComputeOperation<String,Integer>("key", (k, v) -> v1);
    private final MapOperation<String, Integer> compute2 = new ComputeOperation<String,Integer>("key", (k, v) -> v2);
    
    Map<String, Integer> map_seq1 = new HashMap<>();
    Map<String, Integer> map_seq2 = new HashMap<>();

    @Actor
    public void actor1(){
        compute1.run(map);
    }

    @Actor
    public void actor2(){
        compute2.run(map);
    }

    @Arbiter
    public void arbiter(II_Result r) {
        
        //Sequence 1 
        map_seq1.compute("key", (k, v) -> v1);
        map_seq1.compute("key", (k, v) -> v2);

        //Sequence 2
        map_seq2.compute("key", (k, v) -> v2);
        map_seq2.compute("key", (k, v) -> v1);

        boolean seq1 = Objects.equals(map_seq1.get("key"), map.get("key"));
        boolean seq2 = Objects.equals(map_seq2.get("key"), map.get("key"));

        r.r1 = map.containsKey("key") ? 1 : 0;
        r.r2 = (map.get("key") == v1 || map.get("key") == v2) ? 1 : 0;

        assert seq1 || seq2 : "Result non linearizable";
    }
}
