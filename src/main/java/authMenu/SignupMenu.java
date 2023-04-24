package authMenu;

import authenticate.RegisterUser;
import model.User;

import java.io.Console;
import java.util.Random;
import java.util.Scanner;

public class SignupMenu {
    public static void displaySignupMenu() {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        User user = new User();

        System.out.println("Please enter your first name: ");
        user.setFirstName(sc.nextLine());
        System.out.println("Please enter your last name: ");
        user.setLastName(sc.nextLine());
        System.out.println("Please enter your email: ");
        user.setEmail(sc.nextLine());
        System.out.println("Please enter your password: ");
//        user.setPassword(sc.nextLine());
        Console console = System.console();
        if (console == null) {
            System.out.println("Console is not available. Try again.");
            displaySignupMenu();
        }

        char[] passwordArray = console.readPassword("", "Please Enter your password: ");
        String password = new String(passwordArray);
        user.setPassword(password);
        user.setVerificationPin(rand.nextInt(900000) + 100000);
        RegisterUser.signupUser(user);
    }
}
