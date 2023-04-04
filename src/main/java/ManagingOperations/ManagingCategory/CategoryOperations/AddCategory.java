package ManagingOperations.ManagingCategory.CategoryOperations;

import Login.UserCredential;

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
            UserCredential userCredential = new UserCredential();
            System.out.println("Please enter category you want to add: ");
            String query = "insert into category (category_name, created_by, updated_by) values (?, ?, ?)";
            String category = sc.nextLine();
            String username = userCredential.getUsername();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, category);
            stmt.setString(2, username);
            stmt.setString(3, username);
            stmt.executeUpdate();
            System.out.println("Successfully inserted !!!");
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
