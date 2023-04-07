package dao;

import config.DatabaseConfig;
import session.CurrentUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDao {
    private static Scanner sc = new Scanner(System.in);
    private static DatabaseConfig config = new DatabaseConfig();

    public static void addProduct() {
        try {
            Connection con = config.getConnection();
            String query = "insert into product (product_title, description, price, category_id, discount, brand, created_by, updated_by) values (?,?,?,?,?,?,?,?)";
            System.out.println("Enter product title: ");
            String product_title = sc.nextLine();
            System.out.println("Enter description: ");
            String description = sc.nextLine();
            System.out.println("Enter price: ");
            float price = sc.nextFloat();
            sc.nextLine();
            System.out.println("Enter category_id from below: ");
            CategoryDao.getAllCategories();

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

            stmt2.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addSpecification() {
        try {
            Connection con = config.getConnection();
            System.out.println("Add attribute name: ");
            String attName = sc.nextLine();
            System.out.println("Add attribute value: ");
            String attValue = sc.nextLine();

            String query = "insert into specifications (product_id, attribute_name, attribute_value, created_by, updated_by) values (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, getLastInsertedProductId());
            stmt.setString(2, attName);
            stmt.setString(3, attValue);
            stmt.setString(4, CurrentUser.getCurrentUser());
            stmt.setString(5, CurrentUser.getCurrentUser());
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getLastInsertedProductId() {
        try {
            Connection con = config.getConnection();
            String getId = "select id from product order by id desc limit 1";
            PreparedStatement ps = con.prepareStatement(getId);
            ResultSet idSet = ps.executeQuery();
            int product_id = 0;
            while (idSet.next()) {
                product_id = idSet.getInt(1);
            }
            return product_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean checkIfProductsExists() {
        try {
            Connection con = config.getConnection();
            String query = "select * from product";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No products found.");
                con.close();
                return false;
            }

            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<List<String>> getALlProducts() {
        try {
            Connection con = config.getConnection();
            String query = "select * from product";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            List<List<String>> resultSet = new ArrayList<>();
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(String.valueOf(rs.getInt(1)));
                list.add(rs.getString(2));
                list.add(rs.getString(3));
                list.add(String.valueOf(rs.getFloat(4)));
                list.add(String.valueOf(rs.getInt(5)));
                list.add(String.valueOf(rs.getFloat(6)));
                list.add(rs.getString(7));
                resultSet.add(list);
            }
            con.close();
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<List<String>> getAllSpecifications() {
        try {
            Connection con = config.getConnection();
            String query = "select * from specifications";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            List<List<String>> attributeSet = new ArrayList<>();
            while (rs.next()) {
                List<String> list = new ArrayList<>();
                list.add(String.valueOf(rs.getInt(1)));
                list.add(String.valueOf(rs.getInt(2)));
                list.add(rs.getString(3));
                list.add(rs.getString(4));
                attributeSet.add(list);
            }
            return attributeSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateProductBrand(int id, String newBrand) {

        try {
            Connection con = config.getConnection();
            String query = "update product set brand = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newBrand);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductDiscount(int id, float newDiscount) {

        try {
            Connection con = config.getConnection();
            String query = "update product set discount = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setFloat(1, newDiscount);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductTitle(int id, String newTitle) {

        try {
            Connection con = config.getConnection();
            String query = "update product set product_title = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductDescription(int id, String newDescription) {

        try {
            Connection con = config.getConnection();
            String query = "update product set description = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newDescription);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductPrice(int id, float newPrice) {

        try {
            Connection con = config.getConnection();
            String query = "update product set price = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setFloat(1, newPrice);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfProductExists(int id) {
        try {
            Connection con = config.getConnection();
            String query = "select id from product where id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                con.close();
                return false;
            }
            con.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateProductCategory(int id, int newId) {
        try {
            Connection con = config.getConnection();
            String query = "update product set category_id = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, newId);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfProductSpecificationsExistsForGivenProduct(int id) {
        try {
            Connection con = config.getConnection();
            String query = "select * from specifications where product_id = " + id;
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                con.close();
                return false;
            }

            con.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getAllProductSpecifications(int id) {
        try {
            Connection con = config.getConnection();
            String query = "select * from specifications where product_id = " + id;
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " Product ID: " + rs.getInt(2) +
                        " Attribute Name: " + rs.getString(3) + " Attribute Value: " + rs.getString(4));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfProductSpecificationExists(int specId) {
        try {
            Connection con = config.getConnection();
            String query = "select * from specifications where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, specId);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                con.close();
                return false;
            }

            con.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateProductSpecificationAttributeName(int specId, String newAttName) {
        try {
            Connection con = config.getConnection();
            String query = "update specifications set attribute_name = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newAttName);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, specId);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductSpecificationAttributeValue(int specId, String newAttValue) {
        try {
            Connection con = config.getConnection();
            String query = "update specifications set attribute_value = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newAttValue);
            preparedStatement.setString(2, CurrentUser.getCurrentUser());
            preparedStatement.setInt(3, specId);
            preparedStatement.executeUpdate();
            System.out.println("Successfully updated !!!");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
