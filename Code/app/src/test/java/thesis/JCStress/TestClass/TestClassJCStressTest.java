package thesis.JCStress.TestClass;


import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.TestClass.TestClass.TestClass;
import thesis.TestClass.TestClass.TestClassOperation;
import thesis.TestClass.TestClass.Operations_TestClass.Add;
import thesis.TestClass.TestClass.Operations_TestClass.Remove;

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
@Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class TestClassJCStressTest {
    
    TestClass<String> list = new TestClass<>();
    private TestClass<String> observed;
    String addValue = "v1";
    String removeVal = "v1";

    private final TestClassOperation<String> add = new Add<String>(addValue);
    private final TestClassOperation<String> remove = new Remove<String>(removeVal);


    @Actor
    public void actor1(){
        add.run(list);
    }

    @Actor
    public void actor2(){
        remove.run(list);
    }


    @Actor
    public void actor3(){
        observed = new TestClass<>(list);
    }

    @Arbiter
    public void arbiter(Z_Result r){
    
    TestClass<String> seq1 = new TestClass<>();
    seq1.add(addValue);
    seq1.remove(removeVal);

    TestClass<String> seq2 = new TestClass<>();

    seq2.remove(removeVal);
    seq2.add(addValue);



    boolean isValid =
        observed.equals(seq1) || observed.equals(seq2);

    r.r1 = isValid;
        

    }
}
