package thesis.JCStress;

import java.util.HashSet;
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
import thesis.Examples.Users.Users;
import thesis.Examples.Users.UsersMain;

@JCStressTest
@Outcome(id = "true", expect = Expect.ACCEPTABLE)
@Outcome(id = "false", expect = Expect.FORBIDDEN)
@State
public class UsersJCStressTest {
    
    private final UsersMain users = new UsersMain();

    static final Set<HashSet<Users>> expected = ValidPermutations.permutations(
        List.of(
            Operations.add(new Users("alice", "DK", true)),
            Operations.remove(new Users("alice", "DK", true)),
            Operations.add(new Users("alice", "DK", false)),
            Operations.add(new Users("peter", "US", true)),
            Operations.snapshot()
        ), 
        HashSet::new
        );

    @Actor
    public void actor1(){
        users.addUser("alice", "DK");
    }

    @Actor
    public void actor2(){
        users.removeUser("alice");
    }

    @Actor
    public void actor3(){
        users.setStatus("alice", false);
    }

    @Actor
    public void actor4(){
        users.addUser("peter", "US");
    }

    @Arbiter
    public void arbiter(Z_Result r){
        Set<Users> result = new HashSet<>(users.snapshot());
        r.r1 = expected.contains(result);
    }

}
