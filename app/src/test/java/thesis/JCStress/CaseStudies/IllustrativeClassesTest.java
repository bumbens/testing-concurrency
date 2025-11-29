package thesis.JCStress.CaseStudies;

import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;
import org.openjdk.jcstress.infra.results.L_Result;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.Oracle;
import thesis.CaseStudies.IllustrativeExamples;
import thesis.CaseStudies.Counter.Counter;
import thesis.CaseStudies.Counter.CounterSafe;
import thesis.Collections.Operations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;

public class IllustrativeClassesTest {
    @JCStressTest
    @Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
    @Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Unexpected state")
    @State
    public static class ArrayListIllustrativeJCStressTest {
        // JCStress tests for Illustrative ArrayList implementation
        
        private final IllustrativeExamples.ArrayListIllustrative<String> list = new IllustrativeExamples.ArrayListIllustrative<String>();

        static final Set<ArrayList<String>> Expected = thesis.Oracle.sequences(
            List.of(
                Operations.add(0,0, "item1"),
                Operations.add(1, 0, "item2"),
                Operations.remove(2, 0, "item1")
            ), 
            ArrayList::new);

        @Actor
        public void actor1() {
            list.add("item1");
        }

        @Actor
        public void actor2() {
            list.add("item2");  
        }

        @Actor
        public void actor3() {
            list.remove("item1");
        }

        @Arbiter
        public void arbiter(Z_Result r) {
            List<String> snapshot = new ArrayList<>(list.snapshot());
            r.r1 = Expected.contains(snapshot);
        }

    }

    @JCStressTest
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both increments visible")
    @Outcome(expect = Expect.FORBIDDEN, desc = "Lost update occurred")
    @State
    public static class CounterDummyJCStressTest {
        // JCStress tests for Dummy Counter implementation
        
        private final Counter counter = new Counter();
        @Actor
        public void actor1() {
            counter.increment();
        }

        @Actor
        public void actor2() {
            counter.increment();  
        }


        @Arbiter
        public void arbiter(I_Result r) {
            int value = counter.get();
            r.r1 = value;
        }

    }

    @JCStressTest
    @Outcome(id = "2", expect = Expect.ACCEPTABLE, desc = "Both increments visible")
    @Outcome(expect = Expect.FORBIDDEN, desc = "Lost update occurred")
    @State
    public static class CounterFixedJCStressTest {
        // JCStress tests for Dummy Counter implementation
        
        private final CounterSafe counter = new CounterSafe();
        @Actor
        public void actor1() {
            counter.increment();
        }

        @Actor
        public void actor2() {
            counter.increment();  
        }


        @Arbiter
        public void arbiter(I_Result r) {
            int value = counter.get();
            r.r1 = value;
        }

    }



}


