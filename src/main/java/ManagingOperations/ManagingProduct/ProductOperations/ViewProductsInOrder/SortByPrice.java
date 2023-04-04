package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import com.google.protobuf.Internal;

import java.util.Collections;
import java.util.List;

public class SortByPrice {
    List<List<String>> resultSet;

    public SortByPrice(List<List<String>> resultSet) {
        this.resultSet = resultSet;
    }

    public List<List<String>> sortByPrice() {
        for (int i = 0; i < resultSet.size(); i++) {
            for (int j = 0; j < resultSet.size() - i - 1; j++) {
                if (Float.parseFloat(resultSet.get(j).get(3)) < Float.parseFloat(resultSet.get(j+1).get(3))) {
                    Collections.swap(resultSet, j, j+1);
                }
            }
        }
        return resultSet;
    }
}
