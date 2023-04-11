package session;

import model.User;

public class LoggedInUser {
    //TODO : Use "User" field
    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
