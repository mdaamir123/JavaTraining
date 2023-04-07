package ManagingOperations;

import ManagingOperations.ManagingCategory.CategoryManagement;
import ManagingOperations.ManagingProduct.ProductManagement;
import login.Authenticate;
import model.User;
import session.CurrentUser;

import java.util.Scanner;

public class OnlineShoppping {

    private static final String HOST = "jdbc:mysql://localhost:3306/onlineshopping";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {

        User userLogin = initiateLogin();
        User validUser = Authenticate.isValidUser(userLogin);

        if (validUser == null) {
            System.out.println("Please enter valid username and password.");
            return;
        }

        System.out.println("Login successful !!!");
        System.out.println("Welcome " + validUser.getUserName() + ". Your role is " + ((validUser.getRole() == 1) ? "Admin." : "end user."));

        CurrentUser.setCurrentUser(validUser.getUserName());

        int selectedMenuID = getMenuId();

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

    public static int getMenuId() {
        System.out.println("Please choose one option: ");
        System.out.println("1: Category Management");
        System.out.println("2: Product Management");

        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}

