package ManagingOperations.ManagingProduct.ProductOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AddProduct {
    private Connection con;
    private Scanner sc;

    public AddProduct(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void addProduct() {
        try {
            String catQuery = "select * from category";
            PreparedStatement stmt = con.prepareStatement(catQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No categories are present. Kindly add one before.");
                con.close();
                return;
            }

            String query = "insert into product (product_title, description, price, category_id) values (?,?,?,?)";
            System.out.println("Enter product title: ");
            String product_title = sc.nextLine();
            System.out.println("Enter description: ");
            String description = sc.nextLine();
            System.out.println("Enter price: ");
            float price = sc.nextFloat();
            sc.nextLine();
            System.out.println("Enter category_id from below: ");
            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " CATEGORY: " + rs.getString(2));
            }
            int category = sc.nextInt();
            sc.nextLine();

            PreparedStatement stmt2 = con.prepareStatement(query);
            stmt2.setString(1, product_title);
            stmt2.setString(2, description);
            stmt2.setFloat(3, price);
            stmt2.setInt(4, category);
            stmt2.executeUpdate();
            System.out.println("Successfully inserted !!!");
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
