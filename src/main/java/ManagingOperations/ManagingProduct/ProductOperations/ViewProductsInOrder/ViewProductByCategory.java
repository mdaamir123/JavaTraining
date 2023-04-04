package ManagingOperations.ManagingProduct.ProductOperations.ViewProductsInOrder;

import ManagingOperations.ManagingCategory.CategoryOperations.ViewCategoriesInOrder.ShowCategories.PrintCategories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ViewProductByCategory {
    List<List<String>> resultSet;
    List<List<String>> products = new ArrayList<>();
    Scanner sc;
    Connection con;

    public ViewProductByCategory(List<List<String>> resultSet, Connection con, Scanner sc) {
        this.resultSet = resultSet;
        this.con = con;
        this.sc = sc;
    }

    public void printCategories() {
       try {
           String query = "select * from category";
           PreparedStatement preparedStatement = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
           ResultSet rs = preparedStatement.executeQuery();
           if(!rs.next()) {
               System.out.println("There are no categories available. Kindly add one before.");
               con.close();
               return;
           }

           rs.previous();
           while (rs.next()) {
               System.out.println("Category ID: " + rs.getInt(1) + " Category Name: " + rs.getString(2));
           }

           con.close();
       }
       catch (Exception e) {
           e.printStackTrace();
       }
    }

    public List<List<String>> viewProductByCategory() {
        printCategories();
        System.out.println("Enter id of the category: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (var product : resultSet) {
            if(Integer.parseInt(product.get(4)) == id) {
                products.add(product);
            }
        }
        return products;
    }

}
