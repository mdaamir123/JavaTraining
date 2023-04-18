package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import display.Display;
import model.Product;
import model.Specification;

import java.util.List;
import java.util.Scanner;

public class ViewProductById {
    public static void getProductById(List<Product> products, List<Specification> specifications) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter id of product.");
        int id = sc.nextInt();
        sc.nextLine();

        Display.printProductById(products, specifications, id);
    }
}
