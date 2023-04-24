package ManagingOperations;

import ManagingOperations.ManagingCategory.CategoryManagement;
import ManagingOperations.ManagingProduct.ProductManagement;
import authMenu.LoginMenu;
import authMenu.SignupMenu;
import enums.UserRoles;
import session.LoggedInUser;
import java.util.Scanner;

public class OnlineShopping {

    public static void main(String[] args) {
        int choice = displayMenuAndSelect();

        final int LOGIN = 1;
        final int SIGNUP = 2;
        switch (choice) {
            case LOGIN:
                LoginMenu.displayLoginMenu();
                break;
            case SIGNUP:
                SignupMenu.displaySignupMenu();
                break;
            default:
                System.out.println("Please enter valid choice:");
                main(null);
                break;
        }
        displayMenuBasedOnAccess();

    }

    public static int displayMenuAndSelect() {
        System.out.println("Please select from below choices:");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static int displayMainMenuAndSelect() {
        System.out.println("Please choose one option: ");
        System.out.println("1: Category Management");
        System.out.println("2: Product Management");
        System.out.println("3. Logout");
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

    public static void adminOptions() {
        int selectedMenuID = displayMainMenuAndSelect();
        final int CATEGORY_MANAGEMENT = 1;
        final int PRODUCT_MANAGEMENT = 2;
        final int LOGOUT = 3;
        switch (selectedMenuID) {
            case CATEGORY_MANAGEMENT:
                CategoryManagement obj1 = new CategoryManagement();
                obj1.handleCategoryManagement();
                break;
            case PRODUCT_MANAGEMENT:
                ProductManagement obj2 = new ProductManagement();
                obj2.handleProductManagement();
                break;
            case LOGOUT:
                main(null);
                break;
            default:
                System.out.println("Enter valid input.");
                adminOptions();
                break;
        }
    }

    public static void displayMenuBasedOnAccess() {
        if (LoggedInUser.currentUser.getUserRole().getUserRoleId() == UserRoles.ADMIN.getValue()) {
            adminOptions();
        } else {
            ProductManagement obj2 = new ProductManagement();
            obj2.handleProductManagement();
        }
    }

}

