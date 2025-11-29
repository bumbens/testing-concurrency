package thesis.JCStress.CaseStudies;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.L_Result;

import thesis.CaseStudies.StringNumberer.NumberedString;
import thesis.CaseStudies.StringNumberer.StringNumberer;

public class StringNumbererJCStress {
    
    @JCStressTest
    @Outcome(id = "same", expect = Expect.ACCEPTABLE, desc = "Both saw the same instance.")
    @Outcome(id = "different", expect = Expect.FORBIDDEN, desc = "Saw different instances.")
    @State
    public static class FindOrAddTest {
        final StringNumberer numberer = new StringNumberer();
        volatile NumberedString n1, n2;

        @Actor
        public void actor1(){
            n1 = numberer.findOrAdd("test");
        }
        
        @Actor
        public void actor2(){
            n2 = numberer.findOrAdd("test");
        }
        
        @Arbiter
        public void arbiter(L_Result r){
            r.r1 = (n1 == n2) ? "same" : "different";
        }
    }
    

@JCStressTest
@Outcome(id = "ok",    expect = Expect.ACCEPTABLE, desc = "All visible.")
@Outcome(id = "none",       expect = Expect.ACCEPTABLE, desc = "None visible.")
@Outcome(id = "stale",      expect = Expect.FORBIDDEN, desc = "Some visible, some not.")
@State
public static class FindTest {

    private static final String A = "FB";
    final StringNumberer map = new StringNumberer();
    volatile int phase; 

    @Actor
    public void writer() {
        map.findOrAdd(A); 
        phase = 1;        // plain write, no HB
    }

    @Actor
    public void reader(L_Result r) {
        int p = phase;            // plain read, no HB
        if (p == 0) { r.r1 = "none"; return; }
        r.r1 = (map.find(A) != null) ? "ok" : "stale";

    }
    
}

}

// -------------------------------------------------------------------------
// I see where the problem is, but I can't code the solution (test)