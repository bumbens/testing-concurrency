package thesis.JCStress.BrokenClasses;

import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;
import org.openjdk.jcstress.infra.results.L_Result;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.ValidPermutations;
import thesis.Collections.Operations;
import thesis.Examples.BuggyExamples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;

public class BrokenClassesTest {
    @JCStressTest
    @Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
    @Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Unexpected state")
    @State
    public static class ArrayListBrokenJCStressTest {
        // JCStress tests for Broken ArrayList implementation
        
        private final BuggyExamples.ArrayListBroken<String> list = new BuggyExamples.ArrayListBroken<String>();

        static final Set<ArrayList<String>> Expected = thesis.ValidPermutations.permutations(
            List.of(
                Operations.add("item1"),
                Operations.add("item2"),
                Operations.remove("item1"),
                Operations.snapshot()
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
    public static class CounterBrokenJCStressTest {
        // JCStress tests for Broken Counter implementation
        
        private final BuggyExamples.CounterBroken counter = new BuggyExamples.CounterBroken();


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
            int value = counter.getCount();
            r.r1 = value;
        }

    }

    @JCStressTest
    @Outcome(id = "0,null", expect = Expect.ACCEPTABLE, desc = "Value visible")
    @Outcome(id = "1,X", expect = Expect.ACCEPTABLE, desc = "Value visible")
    @Outcome(id = "1,null", expect = Expect.FORBIDDEN, desc = "Value not visible")
    @State
    public static class MessagePassingBrokenJCStressTest {
        // JCStress tests for Broken Message Passing implementation

        private final BuggyExamples.MessagePassingBroken messagePassing = new BuggyExamples.MessagePassingBroken();
        
        @Actor
        public void actor1() {
            messagePassing.put("X");
        }

        @Actor
        public void actor2(L_Result r) { 
            boolean flag = messagePassing.getFlag();
            Object obj = flag ? messagePassing.getObject() : null;
            String outcome = (flag ? "1" : "0") + "," + (obj == null ? "null" : "X");
            r.r1 = outcome;
        }
    }

    @JCStressTest
    @Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
    @Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Different instances")
    @State
    public static class CacheBrokenJCStressTest {
        
        // JCStress test for Broken Cache implementation

        private final BuggyExamples.CacheBroken cache = new BuggyExamples.CacheBroken();
        // obj1 and obj2 are volatile, so the arbiter is guaranteed to see what each actor wrote
        private volatile Object obj1, obj2;

        @Actor
        public void actor1(){
            obj1 = cache.get("c");
        }

        @Actor
        public void actor2(){
            obj2 = cache.get("c");
        }

        @Arbiter
        public void arbiter(Z_Result r) {
            r.r1 = (obj1 == obj2);
        }

    }
        
    @JCStressTest
    @Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "Set contains 'x")
    @Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Missing 'x")
    @State
    public static class AIAJCStressTest {

        // JCStress test for Broken AddIfAbsent implementation
        private final BuggyExamples.AddIfAbsentBroken addIfAbsentBroken = new BuggyExamples.AddIfAbsentBroken();

        static final Set<ArrayList<String>> Expected = ValidPermutations.permutations(
            List.of(
                Operations.addIfAbsent("x"),
                Operations.remove("x"),
                Operations.snapshot()
            ), 
            ArrayList::new);

        @Actor
        public void actor1(){
            addIfAbsentBroken.addIfAbsent("x");
        }

        @Actor
        public void actor2(){
            addIfAbsentBroken.remove("x");
        }

        @Arbiter
        public void arbiter(Z_Result r){
            List<String> outcome = new ArrayList<>(addIfAbsentBroken.snapshot());
            r.r1 = Expected.contains(outcome);
        }

    }



}


