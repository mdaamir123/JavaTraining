package authMenus;

import authenticate.RegisterUser;
import model.User;

import java.util.Scanner;

public class SignupMenu {
    public static void displaySignupMenu() {
        Scanner sc = new Scanner(System.in);
        User user = new User();

        System.out.println("Please enter your first name: ");
        user.setFirstName(sc.nextLine());
        System.out.println("Please enter your last name: ");
        user.setLastName(sc.nextLine());
        System.out.println("Please enter your email: ");
        user.setEmail(sc.nextLine());
        System.out.println("Please enter your password: ");
        user.setPassword(sc.nextLine());

        RegisterUser.signupUser(user);
    }
}
