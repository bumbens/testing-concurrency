package thesis;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.infra.results.L_Result;


//how to run a specific test -- gradle

//specific test for each property

//how to represent properties in JCStress which are not obvious

//pre-conditions, post-conditions

//how to define sequential and parallel (expected outcome should be the same)

//c1.iint == c2.int

//sequential - both ways, if one fits then it's fine
@JCStressTest
@Outcome(id="null", expect = Expect.FORBIDDEN, desc = "null should never be observed")
@Outcome(id="Java.lang.Object", expect = Expect.ACCEPTABLE, desc = "Published object")
public class ObjectJCStressTest {

    Object object = new Object();

    @Actor
    public void writer(){
        object.set(new Object());
    }

    @Actor
    public void reader(L_Result r){
        Object v = object.get();
        r.r1 = v;
    }
    
}
