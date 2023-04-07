package login;

import model.User;
import registeredusers.RegisteredUsers;

public class Authenticate {
    private static RegisteredUsers registeredUsers = new RegisteredUsers();
    public static User isValidUser(User user) {
        return registeredUsers.validateUser(user);
    }
}
