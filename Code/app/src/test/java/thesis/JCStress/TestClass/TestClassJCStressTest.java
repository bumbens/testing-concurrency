package thesis.JCStress.TestClass;


import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.TestClass.OperationTypes;
import thesis.TestClass.TestClass;
import thesis.TestClass.VPNew;
import thesis.TestClass.ValidPermutations;
import thesis.TestClass.OperationsTC.Add;
import thesis.TestClass.OperationsTC.Remove;
import thesis.TestClass.OperationsTC.TestClassOperation;

//force bug or sth
@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
@Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class TestClassJCStressTest {
    
        static final String addV1 = "v1";
        static final String addV2 = "v2";
        static final String removeVal = "v2";

    static final Set<TestClass<String>> Expected = VPNew.permutations(
        List.of(
            OperationTypes.add(addV1),
            OperationTypes.remove(removeVal),
            OperationTypes.snapshot()
        ));

        final TestClassOperation<String> add_v1 = new Add<String>(addV1);
        final TestClassOperation<String> remove_v2 = new Remove<String>(removeVal);
    TestClass<String> list = new TestClass<>();
    TestClass<String> observed;
   

    @Actor
    public void actor1(){
        list.add(addV1);  
    }

    @Actor
    public void actor2(){
        list.remove(removeVal);
    }

    //Thread safety - theory and how is it related? 
    //WHY? What it means in relation to my tests? 
    //Examples

    //thread scheduler - running man times


    @Arbiter
    public void arbiter(Z_Result r){


    r.r1 = Expected.contains(list);
        

    }
}
