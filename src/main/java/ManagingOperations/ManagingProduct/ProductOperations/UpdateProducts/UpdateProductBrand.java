package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import exception.DAOLayerException;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductBrand {
    public void updateProductBrand(int id) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter new product brand. Leave blank if don't want to modify.");
            String newBrand = sc.nextLine();

            if (newBrand.trim().length() == 0) {
                return;
            }
            ProductDao.updateProductBrand(id, newBrand);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
