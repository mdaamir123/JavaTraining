package ManagingOperations;

import ManagingOperations.ManagingCategory.CategoryManagement;
import ManagingOperations.ManagingProduct.ProductManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class OnlineShoppping {

        private static final String  HOST = "jdbc:mysql://localhost:3306/onlineshopping";
        private static  final String USERNAME = "root";
        private static final String PASSWORD = "";

        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in);

            try {
                Connection con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);

                System.out.println("Please choose one option: ");
                System.out.println("1: Category Management");
                System.out.println("2: Product Management");
                int n = sc.nextInt();
                sc.nextLine();

                switch (n) {
                    case 1:
                        CategoryManagement obj1 = new CategoryManagement(con, sc);
                        obj1.handleCategoryManagement();
                        break;
                    case 2:
                        ProductManagement obj2 = new ProductManagement(con, sc);
                        obj2.handleProductManagement();
                        break;
                    default:
                        System.out.println("Enter valid input.");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

