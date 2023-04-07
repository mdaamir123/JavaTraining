package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import dao.ProductDao;

import java.util.Scanner;

public class UpdateProductPrice {
    public void updateProductPrice(int id) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter new product price. Leave blank if don't want to modify.");
        float newPrice = sc.nextFloat();
        sc.nextLine();
        if(String.valueOf(newPrice).trim().length() == 0) {
            return;
        }

        ProductDao.updateProductPrice(id, newPrice);

    }
}
