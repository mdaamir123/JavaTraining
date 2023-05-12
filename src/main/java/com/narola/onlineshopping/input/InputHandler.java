package com.narola.onlineshopping.input;

import com.narola.onlineshopping.menu.SignupMenu;
import com.narola.onlineshopping.validation.InputValidator;

import java.io.Console;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHandler {

    public static int getIntInput() {
        Scanner sc = new Scanner(System.in);
        int input;
        try {
            input = sc.nextInt();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please enter valid input.");
            input = getIntInput();
        } catch (NoSuchElementException e) {
            System.out.println("Please enter valid input.");
            input = getIntInput();
        }
        return input;
    }

    public static String getStrInput() {
        return getStrInput(false);
    }

    public static String getStrInput(boolean isOptional) {
        Scanner sc = new Scanner(System.in);
        String input;
        try {
            input = sc.nextLine();
            if (!isOptional && InputValidator.isEmpty(input)) {
                System.out.println("Input cannot be empty.");
                input = getStrInput(isOptional);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Exception occurred while taking input.");
            input = getStrInput(isOptional);
        }
        return input;
    }

    public static float getFloatInput() {
        Scanner sc = new Scanner(System.in);
        float input;
        try {
            input = sc.nextFloat();
            sc.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Please enter valid input.");
            input = getFloatInput();
        } catch (NoSuchElementException e) {
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

            if (InputValidator.isEmpty(password)) {
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
