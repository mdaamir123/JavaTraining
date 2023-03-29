package ManagingOperations.ManagingProduct.ProductOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShowProducts {
    private Connection con;

    public ShowProducts(Connection con) {
        this.con = con;
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
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " PRODUCT_TITLE: " + rs.getString(2) + " DESCRIPTIPON: " + rs.getString(3) + " PRICE: " + rs.getInt(4) + " CATEGORY_ID: " + rs.getInt(5));
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
