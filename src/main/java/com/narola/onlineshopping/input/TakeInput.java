package com.narola.onlineshopping.input;

import com.narola.onlineshopping.menu.SignupMenu;
import com.narola.onlineshopping.validation.Validation;

import java.io.Console;
import java.util.Scanner;

public class TakeInput {

    public static int getIntInput() {
        Scanner sc = new Scanner(System.in);
        int choice;
        try {
            choice = sc.nextInt();
            sc.nextLine();
        } catch (Exception e) {
            System.out.println("Please enter valid input.");
            choice = getIntInput();
        }
        return choice;
    }

    public static String getStrInput() {
        Scanner sc = new Scanner(System.in);
        String input;
        try {
            input = sc.nextLine();
            if (Validation.isEmpty(input)) {
                System.out.println("Input cannot be empty.");
                input = getStrInput();
            }
        } catch (Exception e) {
            System.out.println("Please enter valid input.");
            input = getStrInput();
        }
        return input;
    }

    public static float getFloatInput() {
        Scanner sc = new Scanner(System.in);
        float input;
        try {
            input = sc.nextFloat();
            sc.nextLine();
            if (input < 0) {
                System.out.println("Please enter valid value.");
                input = getFloatInput();
            }
        } catch (Exception e) {
            System.out.println("Please enter valid input.");
            input = getFloatInput();
        }
        return input;
    }

    public static String getPassword() {
        String password;
        try {
            Console console = System.console();
            if (console == null) {
                System.out.println("Console is not available. Try again.");
                SignupMenu.displaySignupMenu();
            }
            char[] passwordArray = console.readPassword("", "Please Enter your password: ");
            password = new String(passwordArray);

            if (Validation.isEmpty(password)) {
                System.out.println("Password cannot be empty.");
                password = getPassword();
            }
            return password;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            password = getPassword();
        }
        return password;
    }
}
