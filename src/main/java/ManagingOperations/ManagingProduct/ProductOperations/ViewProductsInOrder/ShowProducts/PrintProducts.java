package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder.ShowProducts;

import java.util.List;

public class PrintProducts {
    public void printProducts(List<List<String>> resultSet) {
        if(resultSet.size() == 0) {
            System.out.println("There are no products available.");
        }

        for (var product : resultSet) {
            System.out.println("Product ID: " + product.get(0) + " Product Title: " + product.get(1)
            + " Price: " + product.get(3) + " Discount: " + product.get(5) + "% Category ID: " + product.get(4)
            + " Brand " + product.get(6));
        }
    }
}
