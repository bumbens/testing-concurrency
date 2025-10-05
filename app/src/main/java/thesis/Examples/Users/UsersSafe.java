package thesis.Examples.Users;

import java.util.HashSet;
import java.util.Set;

public class UsersSafe {
    private final Set<String> users = new HashSet<>(); 

    public void addUser(String user) {
        synchronized(users){
            users.add(user);
        }
    }

    public void removeUSer(String user) {
        synchronized(users){
            users.remove(user);
        }
    }

    public Set<String> getUsers() {
        return new HashSet<>(users);
    }
}
