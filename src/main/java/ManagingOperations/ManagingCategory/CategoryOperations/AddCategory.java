package ManagingOperations.ManagingCategory.CategoryOperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class AddCategory {
    private Connection con;
    private Scanner sc;

    public AddCategory(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void addCategory() {
        try {
            System.out.println("Please enter category you want to add: ");
            String query = "insert into category (category_name) values (?)";
            String category = sc.nextLine();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, category);
            stmt.executeUpdate();
            System.out.println("Successfully inserted !!!");
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
