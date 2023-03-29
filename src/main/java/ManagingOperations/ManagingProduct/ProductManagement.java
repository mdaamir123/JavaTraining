package ManagingOperations.ManagingProduct;

import ManagingOperations.ManagingProduct.ProductOperations.AddProduct;
import ManagingOperations.ManagingProduct.ProductOperations.ShowProducts;
import ManagingOperations.ManagingProduct.ProductOperations.UpdateProduct;

import java.sql.Connection;
import java.util.Scanner;

public class ProductManagement {
    private Connection con;
    private Scanner sc;

    public ProductManagement(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void handleProductManagement() {
        try {
            System.out.println("Please choose one option: ");
            System.out.println("1. View");
            System.out.println("2. Add");
            System.out.println("3. Update");
            int x = sc.nextInt();
            sc.nextLine();

            switch (x) {
                case 1:
                    ShowProducts obj1 = new ShowProducts(con);
                    obj1.viewProducts();
                    break;
                case 2:
                    AddProduct obj2 = new AddProduct(con, sc);
                    obj2.addProduct();
                    break;
                case 3:
                    UpdateProduct obj3 = new UpdateProduct(con, sc);
                    obj3.updateProduct();
                    break;
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
