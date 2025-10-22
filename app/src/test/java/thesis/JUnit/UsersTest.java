package thesis.JUnit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import thesis.ValidPermutations;
import thesis.Collections.Operations;
import thesis.Examples.Users.Users;
import thesis.Examples.Users.UsersMain;
import thesis.HTML_Reports.ToFileReport;

public class UsersTest {
    private UsersMain users;

    @Test
    void testUsersCreation(){
        users = new UsersMain();
        users.addUser("alice", "DK");
  
        assert(users.snapshot().contains(new Users("alice", "DK", true)));
        
    }

    @Test
    void testUsersRemove(){
        users = new UsersMain();
        users.addUser("alice", "DK");
        users.removeUser("alice");
 
        assert(users.snapshot().isEmpty());
        
    }

    @Test
    void testUsersSetStatus(){
        users = new UsersMain();
        users.addUser("alice", "DK");
        users.setStatus("alice", false);
  
        assert(users.snapshot().contains(new Users("alice", "DK", false)));
        assert(!users.snapshot().contains(new Users("alice", "DK", true)));
    }

    @Test
    void testGetUser(){
        users = new UsersMain();
        users.addUser("alice", "DK");
        users.addUser("peter", "US");
  
        assert(users.getUser("alice").equals(new Users("alice", "DK", true)));
        assert(users.getUser("peter").equals(new Users("peter", "US", true)));
        assert(users.getUser("bob") == null);
    }

    @RepeatedTest(100)
    void testMultipleOperationsThreads() throws InterruptedException{
        users = new UsersMain();

        final Set<HashSet<Users>> expected = ValidPermutations.permutations(
            List.of(
                Operations.add(new Users("alice", "DK", true)),
                Operations.add(new Users("peter", "US", true)),
                Operations.addIfAbsent(new Users("alice", "DK", false)),
                Operations.remove(new Users("alice", "DK", true)),
                Operations.snapshot()
            ), 
            HashSet::new
            );

        for (int i = 0; i < 5; i++){   
        Thread t1 = new Thread(() -> {
            users.addUser_Bench("alice", "DK");
        });

        Thread t2 = new Thread(() -> {
            users.addUser_Bench("peter", "US");
        });

        Thread t3 = new Thread(() -> {
            users.addIfAbsent_Bench("alice", "DK");
        });

        Thread t4 = new Thread(() -> {
            users.removeUser_Bench("alice");
        });

        Thread t5 = new Thread(() -> {
            Set<Users> snapshot = new HashSet<>(users.snapshot());
            assert(expected.contains(snapshot));
        });

        t1.start(); t2.start(); t3.start(); t4.start(); t5.start();
        t1.join(); t2.join(); t3.join(); t4.join(); t5.join();

        
    }
        Set<Users> finalSet = users.finalSet();
        ToFileReport.saveToFile("UsersTestMultipleOperations.html", finalSet, users.getHistory());
    }

}
