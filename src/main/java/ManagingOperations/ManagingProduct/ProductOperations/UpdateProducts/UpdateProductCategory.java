package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import Login.UserCredential;
import ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ViewProductByCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdateProductCategory {
    Connection con;
    Scanner sc;
    int id;

    public UpdateProductCategory(Connection con, Scanner sc, int id) {
        this.con = con;
        this.sc = sc;
        this.id = id;
    }

    public void printCategories() {
        try {
            String query = "select * from category";
            PreparedStatement preparedStatement = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = preparedStatement.executeQuery();
            if(!rs.next()) {
                System.out.println("There are no categories available. Kindly add one before.");
                System.exit(0);
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("Category ID: " + rs.getInt(1) + "Category Name: " + rs.getString(2));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void updateProductCategory() {
        System.out.println("Enter new category id from the below choices: ");
        int newId = sc.nextInt();
        sc.nextLine();

        if (String.valueOf(newId).trim().length() == 0) {
            return;
        }

        try {
            String query2 = "select id from category where id = ?";
            PreparedStatement stmt2 = con.prepareStatement(query2);
            stmt2.setInt(1, newId);

            ResultSet rss = stmt2.executeQuery();
            if (!rss.next()) {
                System.out.println("ID not found.");
                return;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            UserCredential userCredential = new UserCredential();
            String query = "update product set category_id = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, newId);
            preparedStatement.setString(2, userCredential.getUsername());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
