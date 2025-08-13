package thesis.JCStress.TestClass;


import java.util.List;
import java.util.Set;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.TestClass.OperationTypes;
import thesis.TestClass.TestClass;
import thesis.TestClass.ValidPermutations;

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
@Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class TestClassJCStressTest {
    
    static final Set<TestClass<String>> Expected = ValidPermutations.permutations(
        List.of(
            List.of(OperationTypes.add("v1"), OperationTypes.add("v2")),
            List.of(OperationTypes.remove("v1")),
            List.of(OperationTypes.snapshot())
        ), 100);

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

    r.r1 = Expected.contains(observed);
        

    }
}
