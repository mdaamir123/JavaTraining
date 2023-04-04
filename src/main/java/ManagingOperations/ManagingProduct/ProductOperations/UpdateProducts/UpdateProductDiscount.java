package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import Login.UserCredential;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UpdateProductDiscount {
    Connection con;
    Scanner sc;
    int id;

    public UpdateProductDiscount(Connection con, Scanner sc, int id) {
        this.con = con;
        this.sc = sc;
        this.id = id;
    }

    public void updateProductDiscount() {
        System.out.println("Enter new product discount. Leave blank if don't want to modify.");
        float newDiscount = sc.nextFloat();
        sc.nextLine();
        if(String.valueOf(newDiscount).trim().length() == 0) {
            return;
        }

        try {
            UserCredential userCredential = new UserCredential();
            String query = "update product set discount = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setFloat(1, newDiscount);
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
