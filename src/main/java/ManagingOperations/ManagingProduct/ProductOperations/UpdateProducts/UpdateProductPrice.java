package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import session.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class UpdateProductPrice {
    Connection con;
    Scanner sc;
    int id;

    public UpdateProductPrice(Connection con, Scanner sc, int id) {
        this.con = con;
        this.sc = sc;
        this.id = id;
    }

    public void updateProductPrice() {

        System.out.println("Enter new product price. Leave blank if don't want to modify.");
        float newPrice = sc.nextFloat();
        sc.nextLine();
        if(String.valueOf(newPrice).trim().length() == 0) {
            return;
        }

        try {
            String query = "update product set price = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setFloat(1, newPrice);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
