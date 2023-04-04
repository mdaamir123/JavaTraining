package ManagingOperations.ManagingProduct.ProductOperations;

import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ShowProducts.PrintProductById;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ShowProducts.PrintProducts;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ViewProductByCategory;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.SortByPrice;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.SortByPriceAsc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShowProducts {
    private Connection con;
    private Scanner sc;

    public ShowProducts(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void viewProducts() {
        try {
            String query = "select * from product";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No products found.");
                con.close();
                return;
            }


            rs.previous();
            List<List<String>> resultSet = new ArrayList<>();
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(String.valueOf(rs.getInt(1)));
                list.add(rs.getString(2));
                list.add(rs.getString(3));
                list.add(String.valueOf(rs.getFloat(4)));
                list.add(String.valueOf(rs.getInt(5)));
                list.add(String.valueOf(rs.getFloat(6)));
                list.add(rs.getString(7));
                resultSet.add(list);
            }

            String query2 = "select * from specifications";
            PreparedStatement stmt2 = con.prepareStatement(query2);
            ResultSet rs2 = stmt2.executeQuery();
            List<List<String>> attributeSet = new ArrayList<>();

            while(rs2.next()) {
                List<String> list = new ArrayList<>();
                list.add(String.valueOf(rs2.getInt(1)));
                list.add(String.valueOf(rs2.getInt(2)));
                list.add(rs2.getString(3));
                list.add(rs2.getString(4));
                attributeSet.add(list);
            }

            System.out.println("Select the way of viewing products: ");
            System.out.println("1. View all products.");
            System.out.println("2. View all by highest price.");
            System.out.println("3. View all by lowest price.");
            System.out.println("4. View products by category.");
            System.out.println("5. View Product by id.");

            int choice = sc.nextInt();
            PrintProducts printProducts = new PrintProducts();
            switch (choice) {
                case 1:
                    printProducts.printProducts(resultSet);
                    break;
                case 2:
                    SortByPrice sortByPrice = new SortByPrice(resultSet);
                    printProducts.printProducts(sortByPrice.sortByPrice());
                    break;
                case 3:
                    SortByPriceAsc sortByPriceAsc = new SortByPriceAsc(resultSet);
                    printProducts.printProducts(sortByPriceAsc.sortByPriceAsc());
                    break;
                case 4:
                    ViewProductByCategory viewProductByCategory = new ViewProductByCategory(resultSet, con, sc);
                    printProducts.printProducts(viewProductByCategory.viewProductByCategory());
                    break;
                case 5:
                    PrintProductById printProductById = new PrintProductById();
                    printProductById.printProductById(resultSet, attributeSet, sc);
                    break;
                default:
                    break;
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
