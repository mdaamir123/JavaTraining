package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import exception.DAOLayerException;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductDescription {
    public void updateProductDescription(int id) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter new product description. Leave blank if don't want to modify.");
            String newDescription = sc.nextLine();

            if (newDescription.trim().length() == 0) {
                return;
            }
            ProductDao.updateProductDescription(id, newDescription);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
