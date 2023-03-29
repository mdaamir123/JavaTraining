package ManagingOperations.ManagingProduct.ProductOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdateProduct {
    private Connection con;
    private Scanner sc;

    public UpdateProduct(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void updateProduct() {
        try {
            String query = "select * from product";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No record is present.");
                con.close();
                return;
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " PRODUCT_TITLE: " + rs.getString(2) + " DESCRIPTIPON: " + rs.getString(3) + " PRICE: " + rs.getInt(4) + " CATEGORY_ID: " + rs.getInt(5));
            }
            System.out.println("Enter id of product whose price you want to update: ");
            int y = sc.nextInt();
            sc.nextLine();
            String query2 = "select id from product where id = ?";
            PreparedStatement stmt2 = con.prepareStatement(query2);
            stmt2.setInt(1, y);

            ResultSet rss = stmt2.executeQuery();
            if (!rss.next()) {
                System.out.println("ID not found.");
            } else {
                System.out.println("Enter updated product price: ");
                float newPrice = sc.nextFloat();
                String query3 = "update product set price = ? where id =" + y;
                PreparedStatement stmt3 = con.prepareStatement(query3);
                stmt3.setFloat(1, newPrice);
                stmt3.executeUpdate();
                System.out.println("Successfully updated !!!");
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
