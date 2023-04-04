package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ShowProducts;

import java.util.List;
import java.util.Scanner;

public class PrintProductById {
    public void printProductById(List<List<String>> resultSet, List<List<String>> attributeSet, Scanner sc) {
        System.out.println("Enter id of product.");
        int id = sc.nextInt();
        sc.nextLine();
        boolean exists = false;

        for (var product : resultSet) {
            if(Integer.parseInt(product.get(0)) == id) {
                exists = true;
                System.out.println("ID          :" + product.get(0));
                System.out.println("Title       :" + product.get(1));
                System.out.println("Description :" + product.get(2));
                System.out.println("Price       :" + product.get(3));
                System.out.println("Category ID :" + product.get(4));
                System.out.println("Discount    :" + product.get(5) + "%");
                System.out.println("Brand       :" + product.get(6));

            }
        }

        if(!exists) {
            System.out.println("No product available with id: " + id);
            return;
        }

        for (var spec : attributeSet) {
            if (Integer.parseInt(spec.get(1)) == id) {
                if(exists) {
                    System.out.println("Specifications:");
                    exists = false;
                }

                System.out.println("             Attribute Name: " + spec.get(2));
                System.out.println("             Attribute Value: " + spec.get(3));
            }
        }
    }
}
