package thesis.Examples.Users;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import thesis.Collections.OperationsCollections.Operations_Collections;

public class UsersMain {
    private final Set<Users> users = new HashSet<>();
    /* ------ For benchmarking purposes ------ */
    private List<Operations_Collections<Object>> history = new ArrayList<>();
    private AtomicInteger opCounter = new AtomicInteger();
    /* --------------------------------------- */
    
    public void addUser(String name, String from) {
        users.add(new Users(name, from, true));
    }

    public boolean removeUser(String name) {
        return users.removeIf(u -> u.getName().equals(name));
    }

    public void setStatus(String name, boolean online){
        Users isPresent = users.stream()
            .filter(u -> u.getName().equals(name))
            .findFirst().orElse(null);
        if (isPresent != null){
            users.remove(isPresent);
            users.add(isPresent.setOnline(online));
        }
    }

    public Set<Users> snapshot() {
        return new HashSet<>(users);
    }

    /* ------ For benchmarking purposes ------ */

    private void record(String opType, Object value, int sizeBefore, int sizeAfter) {
        history.add(new Operations_Collections<>(
            Thread.currentThread().getName(), 
            opType, value, sizeBefore, sizeAfter, opCounter.getAndIncrement()
            ));
    }

    public List<Operations_Collections<Object>> getHistory(){
        return new ArrayList<>(history);
    }

    public boolean addUser_Bench(String name, String from) {
        int sizeBefore = users.size();
        boolean added = users.add(new Users(name, from, true));
        int sizeAfter = users.size();
        record("addUser", name, sizeBefore, sizeAfter);
        return added;
    }

    public boolean removeUser_Bench(String name) {
        int sizeBefore = users.size();
        boolean removed = removeUser(name);
        int sizeAfter = users.size();
        record(removed ? "removeUser" : "removeUserFailed", name, sizeBefore, sizeAfter);
        return removed;
    }

    public void setStatus_Bench(String name, boolean online){
        int sizeBefore = users.size();
        setStatus(name, online);
        int sizeAfter = users.size();
        record("setStatus", name, sizeBefore, sizeAfter);
    }

    /* --------------------------------------- */

    public static void main(String[] args) {
        UsersMain main = new UsersMain();
        main.addUser("alice", "DK");
        main.addUser("peter", "US");
        main.setStatus("alice", false);
        main.removeUser("peter");
        System.out.println(main.snapshot());
    }
}
