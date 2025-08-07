package thesis.JCStress;

import java.util.List;
import java.util.Map;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.TestClass;

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
@Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class TestClassJCSwrong {
    
    TestClass<String> list = new TestClass<>();
    private volatile TestClass<String> observed;
    String[] addValue = {"value1", "value2"};
    String removeVal = "value1";

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
    for (String string : addValue) {
        seq1.add(string);
    }
    seq1.remove(removeVal);

    TestClass<String> seq2 = new TestClass<>();
    seq2.remove(removeVal);
    for (String string : addValue) {
        seq2.add(string);
    }
        


    r.r1 = observed.equals(seq1) || observed.equals(seq2);
        

    }
}
