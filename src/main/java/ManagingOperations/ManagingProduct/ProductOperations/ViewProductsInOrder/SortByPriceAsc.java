package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import comparator.SortProductsByPrice;
import model.Product;

import java.util.Collections;
import java.util.List;

public class SortByPriceAsc {
    List<Product> products;

    public SortByPriceAsc(List<Product> products) {
        this.products = products;
    }

    public List<Product> sortByPriceAsc() {
        Collections.sort(products, new SortProductsByPrice());
        return products;
    }
}
