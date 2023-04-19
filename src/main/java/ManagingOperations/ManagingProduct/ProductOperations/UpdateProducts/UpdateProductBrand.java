package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import exceptions.DAOLayerException;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductBrand {
    public void updateProductBrand(int id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new product brand. Leave blank if don't want to modify.");
        String newBrand = sc.nextLine();

        if (newBrand.trim().length() == 0) {
            return;
        }

        try {
            ProductDao.updateProductBrand(id, newBrand);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Successfully updated.");
    }
}
