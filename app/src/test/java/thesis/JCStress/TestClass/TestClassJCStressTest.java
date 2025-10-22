package thesis.JCStress.TestClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.Z_Result;

import thesis.ValidPermutations;
import thesis.Collections.Operations;
import thesis.Examples.ArrayList.ArrayList_Example;


@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE, desc = "All OK")
@Outcome(id = "false", expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class TestClassJCStressTest {
    
    static final String addV1 = "v1";
    static final String addV2 = "v2";
    static final String removeVal = "v2";

    private final ArrayList_Example<String> list = new ArrayList_Example<>();

    static final Set<ArrayList<String>> Expected = ValidPermutations.permutations(
        List.of(
            Operations.add(addV1),
            Operations.add(addV2),
            Operations.remove(removeVal),
            Operations.snapshot()
        ), 
        ArrayList::new);
    

    @Actor
    public void actor1(){
            list.add(addV1);  
    }

    @Actor
    public void actor2(){
            list.add(addV2);
    }

    @Actor
    public void actor3(){
            list.remove(removeVal);
    }

    //Thread safety - theory and how is it related? 
    //WHY? What it means in relation to my tests? 
    //Examples


    @Arbiter
    public void arbiter(Z_Result r){

    List<String> snapshot = list.getSnapshot();
    r.r1 = Expected.contains(snapshot);
        

    }
}


// ArrayList is not a thread-safe class. Without proper synchronization, concurrent modifications can lead to data corruption, inconsistent views of the list, and unexpected behavior.
// In a multi-threaded environment, if multiple threads access and modify an ArrayList simultaneously without synchronization, it can result in lost updates, corrupted data, and runtime exceptions.
// To ensure thread safety when using ArrayList in a concurrent context, you can use synchronization mechanisms such as synchronized blocks, locks, or concurrent collections like CopyOnWriteArrayList or Collections.synchronizedList.
// Proper synchronization ensures that only one thread can modify the ArrayList at a time, preventing data corruption and ensuring consistent views of the list across threads.
// In summary, using ArrayList in a multi-threaded environment without synchronization can lead to unpredictable and erroneous behavior, making it crucial to implement appropriate thread-safety measures.