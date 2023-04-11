package login;

import model.User;

public class Authenticate {
    private static RegisteredUsers registeredUsers = new RegisteredUsers();
    public static User isValidUser(User user) {
        return registeredUsers.validateUser(user);
    }
}
