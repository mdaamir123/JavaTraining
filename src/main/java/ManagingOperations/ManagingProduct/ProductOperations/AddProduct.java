package ManagingOperations.ManagingProduct.ProductOperations;

import config.DatabaseConfig;
import session.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AddProduct {

    Scanner sc = new Scanner(System.in);
    DatabaseConfig config = new DatabaseConfig();

    public void addProduct() {
        try {
            Connection con = config.getConnection();
            String catQuery = "select * from category";
            PreparedStatement stmt = con.prepareStatement(catQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No categories are present. Kindly add one before.");
                con.close();
                return;
            }

            String query = "insert into product (product_title, description, price, category_id, discount, brand, created_by, updated_by) values (?,?,?,?,?,?,?,?)";
            System.out.println("Enter product title: ");
            String product_title = sc.nextLine();
            System.out.println("Enter description: ");
            String description = sc.nextLine();
            System.out.println("Enter price: ");
            float price = sc.nextFloat();
            sc.nextLine();
            System.out.println("Enter category_id from below: ");
            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " CATEGORY: " + rs.getString(2));
            }
            int category = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter discount: ");
            float discount = sc.nextFloat();
            sc.nextLine();
            System.out.println("Enter brand: ");
            String brand = sc.nextLine();
            String username = CurrentUser.getCurrentUser();

            PreparedStatement stmt2 = con.prepareStatement(query);
            stmt2.setString(1, product_title);
            stmt2.setString(2, description);
            stmt2.setFloat(3, price);
            stmt2.setInt(4, category);
            stmt2.setFloat(5, discount);
            stmt2.setString(6, brand);
            stmt2.setString(7, username);
            stmt2.setString(8, username);

            System.out.println("Do ypu want to add specifications ?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            int spec = sc.nextInt();
            sc.nextLine();
            stmt2.executeUpdate();

            if (spec == 1) {

                String getId = "select id from product order by id desc limit 1";
                PreparedStatement ps = con.prepareStatement(getId);
                ResultSet idSet = ps.executeQuery();
                int product_id = 0;
                while (idSet.next()) {
                    product_id = idSet.getInt(1);
                }

                do {
                    System.out.println("Add attribute name: ");
                    String attName = sc.nextLine();
                    System.out.println("Add attribute value: ");
                    String attValue = sc.nextLine();

                    String query2 = "insert into specifications (product_id, attribute_name, attribute_value, created_by, updated_by) values (?,?,?,?,?)";
                    PreparedStatement stmt3 = con.prepareStatement(query2);
                    stmt3.setInt(1, product_id);
                    stmt3.setString(2, attName);
                    stmt3.setString(3, attValue);
                    stmt3.setString(4, username);
                    stmt3.setString(5, username);
                    stmt3.executeUpdate();
                    System.out.println("Press q to quit: ");
                    char pressed = sc.next().charAt(0);
                    sc.nextLine();
                    if (pressed == 'q') {
                        spec = 0;
                    }
                } while (spec == 1);
            } else if (spec != 1) {
                System.out.println("No attributes added to product.");
            }

            System.out.println("Product successfully inserted !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
