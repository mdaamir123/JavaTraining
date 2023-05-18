package com.narola.onlineshoppingV1.session;

import com.narola.onlineshoppingV1.model.User;

public class LoggedInUser {
    public static User currentUser;

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }
}
