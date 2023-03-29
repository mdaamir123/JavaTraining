package ManagingOperations.ManagingCategory.CategoryOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShowCategories {
    private Connection con;

    public ShowCategories(Connection con) {
        this.con = con;
    }

    public void viewCategories() {
        try {
            String query = "select * from category";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No categories found.");
                con.close();
                return;
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " CATEGORY: " + rs.getString(2));
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
