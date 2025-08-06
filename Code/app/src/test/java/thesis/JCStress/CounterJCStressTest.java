package thesis.JCStress;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.I_Result;

import thesis.Counter;



    //mail - is there any documentation???
    // fx >0 - id?

    //fx concurrent hashmap

    @JCStressTest
    @State
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "All OK") //how we can check more complicated outputs (Objects etc)
    @Outcome(id = "1", expect = Expect.FORBIDDEN, desc = "One increment lost due to race condition.")
    @Outcome(id = "0", expect = Expect.FORBIDDEN, desc = "Both increments lost (very unlikely).")
    @Outcome(id = "3", expect = Expect.UNKNOWN, desc = "Test.")
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
