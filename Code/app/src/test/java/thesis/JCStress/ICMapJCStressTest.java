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
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.ObservableConcurrentMap;
import thesis.Operations.MapOperation;
import thesis.Operations.Put;
import thesis.Operations.Remove;

//List + 3 methods (add, remove, contains) - equals which checks content equality; java library - sth to check content equality (list); Arrays Class - check as well 
//Mutation tests 
//Label transition system (threads); parallel

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "Sequential execution")
@Outcome(expect = Expect.FORBIDDEN, desc = "Unexpected state")

@State
public class ICMapJCStressTest {

    private final ObservableConcurrentMap<Object, Object> map = new ObservableConcurrentMap<>();
    private final Object key = "key";
    private final Object value = 1;

    private final MapOperation<Object, Object> put = new Put<>(key, value);
    private final MapOperation<Object, Object> remove = new Remove<>(key);

    private Map<Object, Object> observed;


    @Actor
    public void actor1(){
        // This actor puts a key-value pair into the map
        put.run(map);

    }

    @Actor
    public void actor2(){
        // This actor tries to remove the key that might have been put by actor1
        remove.run(map);
    }

    @Actor
    public void actor3(){
        // Take a snapshot of the map
        // This is to ensure that the observed state is consistent
        // and reflects the operations performed by the actors
        observed = new HashMap<>(map);
        
    }

    @Arbiter
    public void arbiter(Z_Result r) {

    // Sequence 1 (remove before put; key present)
    Map<Object, Object> map_seq1 = new HashMap<>();
    map_seq1.remove(key);
    map_seq1.put(key, value);

    //Sequence 2 (put before remove; key not present)
    Map<Object, Object> map_seq2 = new HashMap<>();
    map_seq1.put(key, value);
    map_seq1.remove(key);



    // Sequential execution
    r.r1 = Objects.equals(observed, map_seq1) 
        || Objects.equals(observed, map_seq2);
}
}
