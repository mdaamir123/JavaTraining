package registeredusers;

import model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteredUsers {
    private static Map<String, List<String>> users = new HashMap<>();
    static {
        users.put("Md.Aamir", List.of("admin", "1"));
        users.put("VJL", List.of("admin", "1"));
        users.put("PMP", List.of("admin", "1"));
        users.put("MS Dhoni", List.of("enduser", "2"));
        users.put("Leo Messi", List.of("enduser", "2"));
        users.put("PRP", List.of("enduser", "2"));
    }

    public User validateUser(User user) {
        if(users.containsKey(user.getUserName()) && users.get(user.getUserName()).get(0).equals(user.getPassword())) {
            user.setRole(Integer.parseInt(users.get(user.getUserName()).get(1)));
            return user;
        }

        return null;
    }
}
