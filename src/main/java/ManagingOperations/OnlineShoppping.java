package ManagingOperations;

import ManagingOperations.ManagingCategory.CategoryManagement;
import ManagingOperations.ManagingProduct.ProductManagement;
import authMenus.LoginMenu;
import authMenus.SignupMenu;
import session.LoggedInUser;

import java.util.Scanner;

public class OnlineShoppping {
    
    public static void main(String[] args) {
        int choice = displayMenuAndSelect();

        switch (choice) {
            case 1:
                LoginMenu.displayLoginMenu();
                break;
            case 2:
                SignupMenu.displaySignupMenu();
                break;
            default:
                System.out.println("Please enter valid choice:");
                break;
        }

        if(LoggedInUser.currentUser.getUserRole().getUserRoleId() == 1) {
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
        else {
            ProductManagement obj2 = new ProductManagement();
            obj2.handleProductManagement();
        }
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

        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }

}

