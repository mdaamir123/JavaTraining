package ManagingOperations;

import ManagingOperations.ManagingCategory.CategoryManagement;
import ManagingOperations.ManagingProduct.ProductManagement;
import config.DatabaseConfig;
import login.Authenticate;
import model.User;
import session.LoggedInUser;

import java.util.Scanner;

public class OnlineShoppping {
    
    public static void main(String[] args) {
        User userLogin = initiateLogin();
        User validUser = Authenticate.isValidUser(userLogin);

        if (validUser == null) {
            System.out.println("Please enter valid username and password.");
            return;
        }

        System.out.println("Login successful !!!");
        System.out.println("Welcome " + validUser.getUserName() + ". Your role is " + ((validUser.getRole() == 1) ? "Admin." : "end user."));

        openDatabaseConnection();
        System.out.println("Database connected successfully.");

        LoggedInUser.setCurrentUser(validUser);

        int selectedMenuID = displayMainMenuAndSelect();
        switch (selectedMenuID) {
            case 1:
                CategoryManagement obj1 = new CategoryManagement();
                obj1.handleCategoryManagement();
                break;
            case 2:
                ProductManagement obj2 = new ProductManagement();
                obj2.handleProductManagement();
                break;
            default:
                System.out.println("Enter valid input.");
                break;
        }

    }

    public static User initiateLogin() {
        Scanner sc = new Scanner(System.in);
        User userLogin = new User();
        System.out.println("Enter username: ");
        userLogin.setUserName(sc.nextLine());
        System.out.println("Enter password: ");
        userLogin.setPassword(sc.nextLine());
        return userLogin;
    }

    public static int displayMainMenuAndSelect() {
        System.out.println("Please choose one option: ");
        System.out.println("1: Category Management");
        System.out.println("2: Product Management");

        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static void openDatabaseConnection() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter database name:");
        String database = sc.nextLine();
        System.out.println("Please enter mysql username:");
        String mysqlUsername = sc.nextLine();
        System.out.println("Please enter mysql password:");
        String mysqlPassword = sc.nextLine();
        try {
            DatabaseConfig.checkDatabaseConnection(database, mysqlUsername, mysqlPassword);
        }
        catch (Exception e) {
            System.out.println("Invalid credentials.");
            System.exit(0);
        }
    }
}

