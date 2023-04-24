package ManagingOperations.ManagingProduct;

import ManagingOperations.ManagingProduct.ProductOperations.AddProduct;
import ManagingOperations.ManagingProduct.ProductOperations.ShowProducts;
import ManagingOperations.ManagingProduct.ProductOperations.UpdateProduct;
import enums.UserRoles;
import session.LoggedInUser;

import java.util.Scanner;

import static ManagingOperations.OnlineShopping.displayMenuBasedOnAccess;

public class ProductManagement {

    public static void handleProductManagement() {
        if (LoggedInUser.currentUser.getUserRole().getUserRoleId() == UserRoles.ADMIN.getValue()) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("Please choose one option: ");
                System.out.println("1. View");
                System.out.println("2. Add");
                System.out.println("3. Update");
                System.out.println("4. Back");
                int choice = sc.nextInt();
                sc.nextLine();

                final int VIEW = 1;
                final int ADD = 2;
                final int UPDATE = 3;
                final int BACK = 4;
                switch (choice) {
                    case VIEW:
                        ShowProducts obj1 = new ShowProducts();
                        obj1.viewProducts();
                        break;
                    case ADD:
                        AddProduct obj2 = new AddProduct();
                        obj2.addProduct();
                        break;
                    case UPDATE:
                        UpdateProduct obj3 = new UpdateProduct();
                        obj3.updateProduct();
                        break;
                    case BACK:
                        displayMenuBasedOnAccess();
                        break;
                    default:
                        System.out.println("Please enter valid input.");
                        break;
                }
                handleProductManagement();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ShowProducts obj1 = new ShowProducts();
            obj1.viewProducts();
        }
    }
}
