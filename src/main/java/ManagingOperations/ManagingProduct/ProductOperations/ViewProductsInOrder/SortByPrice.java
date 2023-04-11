package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import comparators.SortProductsByPrice;
import model.Product;

import java.util.Collections;
import java.util.List;

public class SortByPrice {
    List<Product> products;

    public SortByPrice(List<Product> products) {
        this.products = products;
    }

    public List<Product> sortByPrice() {
        Collections.sort(products, new SortProductsByPrice());
        return products;
    }
}
