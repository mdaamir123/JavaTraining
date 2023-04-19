package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import exceptions.DAOLayerException;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductDiscount {
    public void updateProductDiscount(int id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new product discount. Leave blank if don't want to modify.");
        float newDiscount = sc.nextFloat();
        sc.nextLine();
        if(String.valueOf(newDiscount).trim().length() == 0) {
            return;
        }

        try {
            ProductDao.updateProductDiscount(id, newDiscount);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Successfully updated.");
    }
}
