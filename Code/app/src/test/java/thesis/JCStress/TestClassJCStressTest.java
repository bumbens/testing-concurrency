package thesis.JCStress;


import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.TestClass.TestClass;

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
@Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class TestClassJCStressTest {
    
    TestClass<String> list = new TestClass<>();
    private volatile TestClass<String> observed;
    String[] addValue = {"v1", "v2"};
    String removeVal = "v1";

    @Actor
    public void actor1(){
        for (String string : addValue) {
            list.add(string);
        }
    }

    @Actor
    public void actor2(){
        list.remove(removeVal);
    }

    @Actor
    public void actor3(){
        observed = new TestClass<>(list);
    }

    @Arbiter
    public void arbiter(Z_Result r){
    
    TestClass<String> seq1 = new TestClass<>();
    // add v1 -> add v2 -> SNAPSHOT -> remove v1
    // OR
    // remove v1 -> add v1 -> add v2 -> SNAPSHOT
    seq1.add("v1");
    seq1.add("v2");

    TestClass<String> seq2 = new TestClass<>();
    // add v1 -> add v2 -> remove v1 -> SNAPSHOT
    // OR
    // add v1 -> remove v1 -> add v2 -> SNAPSHOT
    seq2.add("v2");

    TestClass<String> seq3 = new TestClass<>();
    // add v1 -> SNAPSHOT -> remove v1 -> add v2
    seq3.add("v1");

    TestClass<String> seq4 = new TestClass<>();
    // add v1 -> remove v1 -> SNAPSHOT -> add v2
    // OR
    // remove v1 -> SNAPSHOT -> add v1 -> add v2



    boolean isValid =
        observed.equals(seq1)
        || observed.equals(seq2)
        || observed.equals(seq3)
        || observed.equals(seq4);

    r.r1 = isValid;
        

    }
}
