package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import Login.UserCredential;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UpdateProductDescription {
    Connection con;
    Scanner sc;
    int id;

    public UpdateProductDescription(Connection con, Scanner sc, int id) {
        this.con = con;
        this.sc = sc;
        this.id = id;
    }

    public void updateProductDescription() {
        System.out.println("Enter new product description. Leave blank if don't want to modify.");
        String newDescription = sc.nextLine();

        if(newDescription.trim().length() == 0) {
            return;
        }

        try {
            UserCredential userCredential = new UserCredential();
            String query = "update product set description = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newDescription);
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
