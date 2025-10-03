package thesis.JCStress;

import org.checkerframework.checker.units.qual.A;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.L_Result;

import thesis.CounterThreadsLock;

@JCStressTest
@Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "All OK")
@Outcome(expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class TurnstileJCStressTest {
    
    CounterThreadsLock counter = new CounterThreadsLock();

    @Actor
    public void actor1(){
        counter.increment();
    }

    @Actor
    public void actor2(){
        counter.increment();
    }

    @Arbiter
    public void arbiter(L_Result r){
        long res = counter.getCounter();
        r.r1 = res;
        
    }
}
