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
    @Outcome(id = "0,null", expect = Expect.ACCEPTABLE, desc = "Reader ran before writer.")
    @Outcome(id = "1,ok",   expect = Expect.ACCEPTABLE, desc = "Writer ran before reader; value visible.")
    @Outcome(id = "1,null", expect = Expect.FORBIDDEN,  desc = "Writer ran first, but reader did not see value (visibility bug).")
    @State
    public static class FindTest {

        final StringNumberer numberer = new StringNumberer();
        volatile boolean published;

        @Actor
        public void actor1() {
            numberer.findOrAdd("test");
            published = true; 
        }

        @Actor
        public void actor2(L_Result r) {
            Object v = published ? numberer.find("test") : null;
            r.r1 = (published ? "1" : "0") + "," + (v == null ? "null" : "ok");
        }
    }
}

// no matter whether we use synchronized or concurrent map, JCStress tests fail