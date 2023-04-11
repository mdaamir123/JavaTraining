package login;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class RegisteredUsers {
    //TODO : Use USER domain class into List
    private static Map<String, User> users = new HashMap<>();
    static {
        users.put("Md.Aamir", new User("Md.Aamir", "admin", 1));
        users.put("VJL", new User("VJL", "admin", 1));
        users.put("PRP", new User("PRP", "admin", 1));
        users.put("PMP", new User("PMP", "enduser", 2));
        users.put("SDS", new User("SDS", "enduser", 2));
        users.put("SME", new User("SME", "enduser", 2));
    }

    public User validateUser(User user) {
        if(users.containsKey(user.getUserName()) && users.get(user.getUserName()).getPassword().equals(user.getPassword())) {
            user.setRole(users.get(user.getUserName()).getRole());
            return user;
        }

        return null;
    }
}
