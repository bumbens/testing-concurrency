package thesis;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

@JCStressTest
@Outcome(id = {"0", "1"}, expect = Expect.ACCEPTABLE, desc = "Map size is as expected")
@State
public class ICMapJCStressTest {

    private final InstrumentedConcurrentMap<String, Integer> map = new InstrumentedConcurrentMap<>();

    private final MapOperation<String, Integer> put = new PutOperation<>("key", 1);
    private final MapOperation<String, Integer> remove = new RemoveOperation("key");

    @Actor
    public void actor1(){
        put.run(map);
    }

    @Actor
    public void actor2(){
        remove.run(map);
    }

    @Arbiter
    public void arbiter(I_Result r) {
        r.r1 = (map.get("key") == null) ? 0 : 1;
        
        System.out.println("Operation history:");
        for (Operation<String, Integer> op : map.getHistory()) {
            System.out.println(op);
        }
    }
}
