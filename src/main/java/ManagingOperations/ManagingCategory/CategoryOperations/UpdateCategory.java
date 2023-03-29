package ManagingOperations.ManagingCategory.CategoryOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdateCategory {
    private Connection con;
    private Scanner sc;

    public UpdateCategory(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void updateCategory() {
        try {
            String query = "select * from category";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No record is present.");
                con.close();
                return;
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " CATEGORY: " + rs.getString(2));
            }
            System.out.println("Enter id of category you want to update: ");
            int y = sc.nextInt();
            sc.nextLine();
            String query2 = "select id from category where id = ?";
            PreparedStatement stmt2 = con.prepareStatement(query2);
            stmt2.setInt(1, y);

            ResultSet rss = stmt2.executeQuery();
            if (!rss.next()) {
                System.out.println("ID not found.");
            } else {
                System.out.println("Enter updated category name: ");
                String newCategory = sc.nextLine();
                String query3 = "update category set category_name = ? where id =" + y;
                PreparedStatement stmt3 = con.prepareStatement(query3);
                stmt3.setString(1, newCategory);
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
