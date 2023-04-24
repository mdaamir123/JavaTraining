package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import exception.DAOLayerException;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductDiscount {
    public void updateProductDiscount(int id) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter new product discount. Leave blank if don't want to modify.");
            float newDiscount = sc.nextFloat();
            sc.nextLine();
            if (String.valueOf(newDiscount).trim().length() == 0) {
                return;
            }
            ProductDao.updateProductDiscount(id, newDiscount);
            System.out.println("Successfully updated.");
        } catch (DAOLayerException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
