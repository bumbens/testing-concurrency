package thesis.JCStress;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.I_Result;

import thesis.PublishObject;

@JCStressTest
@Outcome(id = "1", expect = Expect.ACCEPTABLE, desc = "Safe publication, write-before-read")
@Outcome(id = "-1", expect = Expect.ACCEPTABLE, desc = "Safe publication, read-before-write")
@Outcome(expect = Expect.FORBIDDEN, desc = "Unexpected state")
@State
public class PublishObjectJCStressTest {


    volatile PublishObject publishObject;

    @Actor
    public void writer() {
        publishObject = new PublishObject();
    }

    @Actor
    public void reader(I_Result r) {
        PublishObject p = publishObject;
        r.r1 = (p == null) ? -1 : p.getValue();
    }
}
