package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import login.UserCredential;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UpdateProductBrand {
    Connection con;
    Scanner sc;
    int id;

    public UpdateProductBrand(Connection con, Scanner sc, int id) {
        this.con = con;
        this.sc = sc;
        this.id = id;
    }

    public void updateProductBrand() {
        System.out.println("Enter new product brand. Leave blank if don't want to modify.");
        String newBrand = sc.nextLine();

        if (newBrand.trim().length() == 0) {
            return;
        }

        try {
            UserCredential userCredential = new UserCredential();
            String query = "update product set brand = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newBrand);
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
