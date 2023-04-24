package ManagingOperations.ManagingProduct.ProductOperations.UpdateProductV2;

import java.util.HashMap;
import java.util.Map;

public class UpdateProductV2 {
    private static Map<Integer, String> map;

    static {
        map = new HashMap<>();
        map.put(1, "product_title");
        map.put(2, "description");
        map.put(3, "price");
        map.put(4, "category_id");
        map.put(5, "discount");
        map.put(6, "brand");
    }

//    public static void updateProduct(int productId, int column) {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Please enter new " + map.get(column));
//        String newValue = sc.nextLine();
//        if(newValue.trim().length() == 0) {
//            System.out.println("Value cannot be blank.");
//            return;
//        }
//        try {
//            ProductDao.updateProduct(productId, map.get(column), newValue);
//            System.out.println("Successfully updated.");
//        } catch (DAOLayerException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
