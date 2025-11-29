package thesis.JCStress.CaseStudies;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.L_Result;

import thesis.CaseStudies.Hudi.TransientReproduced;

@JCStressTest
@Outcome(id = "race", expect = Expect.FORBIDDEN,  
    desc = "Race on ref visibility.")
@Outcome(id = "onlyNull", expect = Expect.ACCEPTABLE_INTERESTING, 
    desc = "Null only observed.")
@Outcome(id = "onlyNonNull",expect = Expect.ACCEPTABLE, 
    desc = "Non-null only observed.")
@State
public class TransientJCStress {

    final TransientReproduced t = new TransientReproduced();

    volatile boolean isNull;
    volatile boolean isNotNull;

    @Actor
    public void actor1() {
        for (int i = 0; i < 1_000; i++) {
            Object obj = t.get();
            if (obj == null)            isNull = true;
            else                        isNotNull = true;
            if (isNull && isNotNull)    break; 
        }
    }

    @Actor
    public void actor2() {
        for (int i = 0; i < 1_000; i++) {
            t.reset();
            t.get(); // initialize ref again
        }
    }

    @Arbiter
    public void arbiter(L_Result r) {
        if (isNull && isNotNull) {
            r.r1 = "race";
        } else if (!isNull && isNotNull) {
            r.r1 = "onlyNonNull";
        } else {
            r.r1 = "onlyNull";
        }
    }
}

