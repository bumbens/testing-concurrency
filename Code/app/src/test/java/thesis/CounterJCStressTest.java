package thesis;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;


    
    @JCStressTest
    @State
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "All OK")
    @Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "One increment lost due to race condition.")
    @Outcome(id = "0", expect = Expect.FORBIDDEN, desc = "Both increments lost (very unlikely).")
    public class CounterJCStressTest {

        Counter counter = new Counter();

        @Actor
        public void actor1(){
            
            counter.increment();
        }

        @Actor
        public void actor2(){
            counter.increment();
        }

        @Arbiter
        public void arbiter(I_Result r){
            r.r1 = counter.get();
        }

}
