package comparators;

import model.Product;

import java.util.Comparator;

public class SortProductsByPrice implements Comparator<Product> {
    @Override
    public int compare(Product o1, Product o2) {
        if(o1.getProductPrice() > o2.getProductPrice()) {
            return -1;
        }
        else if(o1.getProductPrice() == o2.getProductPrice()) {
            return 0;
        }
        return 1;
    }
}
