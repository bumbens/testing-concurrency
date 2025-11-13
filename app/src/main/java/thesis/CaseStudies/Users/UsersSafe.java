package thesis.CaseStudies.Users;

import java.util.HashSet;
import java.util.Set;

public class UsersSafe {
    private final Set<Users> users = new HashSet<>();
    
    public synchronized void addUser(String name, String from) {
        users.add(new Users(name, from, true));
    }

    public synchronized void removeUser(String name) {
        users.removeIf(u -> u.getName().equals(name));
    }

    public synchronized void setStatus(String name, boolean online){
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

    public static void main(String[] args) {
        UsersSafe main = new UsersSafe();
        main.addUser("alice", "DK");
        main.addUser("peter", "US");
        main.setStatus("alice", false);
        main.removeUser("peter");
        System.out.println(main.snapshot());
    }
}
