package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import exceptions.DAOLayerException;
import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductDescription {
    public void updateProductDescription(int id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new product description. Leave blank if don't want to modify.");
        String newDescription = sc.nextLine();

        if(newDescription.trim().length() == 0) {
            return;
        }

        try {
            ProductDao.updateProductDescription(id, newDescription);
        } catch (DAOLayerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Successfully updated.");
    }
}
