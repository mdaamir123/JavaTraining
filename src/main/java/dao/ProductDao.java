package dao;

import config.DatabaseConfig;
import model.Product;
import model.Specification;
import session.LoggedInUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDao {
    private static Scanner sc = new Scanner(System.in);

    public static void addProduct(String product_title, String description, float price, int category, float discount, String brand) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "insert into product (product_title, description, price, category_id, discount, brand, created_by, updated_by) values (?,?,?,?,?,?,?,?)";

            String username = LoggedInUser.getCurrentUser().getUserName();

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

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void addSpecification(Specification specification) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();


            String query = "insert into specifications (product_id, attribute_name, attribute_value, created_by, updated_by) values (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setInt(1, getLastInsertedProductId());
            stmt.setString(2, specification.getSpecAttributeName());
            stmt.setString(3, specification.getSpecAttributeValue());
            stmt.setString(4, LoggedInUser.getCurrentUser().getUserName());
            stmt.setString(5, LoggedInUser.getCurrentUser().getUserName());
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static int getLastInsertedProductId() {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
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
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "select COUNT(*) from product";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Product> getALlProducts() {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "select * from product";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            List<Product> resultSet = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt(1));
                product.setProductTitle(rs.getString(2));
                product.setProductDescription(rs.getString(3));
                product.setProductPrice(rs.getFloat(4));
                product.setProductCategoryId(rs.getInt(5));
                product.setProductDiscount(rs.getFloat(6));
                product.setProductBrand(rs.getString(7));
                product.setCreatedOn(rs.getTimestamp(8).toLocalDateTime());
                product.setUpdatedOn(rs.getTimestamp(9).toLocalDateTime());
                product.setCreatedBy(rs.getString(10));
                product.setUpdatedBy(rs.getString(11));
                resultSet.add(product);
            }

            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Product> getProductsByCategory(int id) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "select * from product where category_id = " + id;
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            List<Product> resultSet = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt(1));
                product.setProductTitle(rs.getString(2));
                product.setProductDescription(rs.getString(3));
                product.setProductPrice(rs.getFloat(4));
                product.setProductCategoryId(rs.getInt(5));
                product.setProductDiscount(rs.getFloat(6));
                product.setProductBrand(rs.getString(7));
                product.setCreatedOn(rs.getTimestamp(8).toLocalDateTime());
                product.setUpdatedOn(rs.getTimestamp(9).toLocalDateTime());
                product.setCreatedBy(rs.getString(10));
                product.setUpdatedBy(rs.getString(11));
                resultSet.add(product);
            }

            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Specification> getAllSpecifications() {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "select * from specifications";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            List<Specification> attributeSet = new ArrayList<>();
            while (rs.next()) {
                Specification specification = new Specification();
                specification.setSpecId(rs.getInt(1));
                specification.setSpecProductId(rs.getInt(2));
                specification.setSpecAttributeName(rs.getString(3));
                specification.setSpecAttributeValue(rs.getString(4));
                specification.setCreatedOn(rs.getTimestamp(5).toLocalDateTime());
                specification.setUpdatedOn(rs.getTimestamp(6).toLocalDateTime());
                specification.setCreatedBy(rs.getString(7));
                specification.setUpdatedBy(rs.getString(8));
                attributeSet.add(specification);
            }
            return attributeSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateProductBrand(int id, String newBrand) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "update product set brand = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newBrand);
            preparedStatement.setString(2, LoggedInUser.getCurrentUser().getUserName());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void updateProductDiscount(int id, float newDiscount) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "update product set discount = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setFloat(1, newDiscount);
            preparedStatement.setString(2, LoggedInUser.getCurrentUser().getUserName());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void updateProductTitle(int id, String newTitle) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "update product set product_title = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newTitle);
            preparedStatement.setString(2, LoggedInUser.getCurrentUser().getUserName());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void updateProductDescription(int id, String newDescription) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "update product set description = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newDescription);
            preparedStatement.setString(2, LoggedInUser.getCurrentUser().getUserName());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void updateProductPrice(int id, float newPrice) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "update product set price = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setFloat(1, newPrice);
            preparedStatement.setString(2, LoggedInUser.getCurrentUser().getUserName());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static boolean checkIfProductExists(int id) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "select COUNT(id) from product where id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateProductCategory(int id, int newId) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "update product set category_id = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, newId);
            preparedStatement.setString(2, LoggedInUser.getCurrentUser().getUserName());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static boolean checkIfProductSpecificationsExistsForGivenProduct(int id) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "select COUNT(*) from specifications where product_id = " + id;
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            return count > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getAllProductSpecifications(int id) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
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
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "select COUNT(*) from specifications where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, specId);
            ResultSet rs = preparedStatement.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            return count > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateProductSpecificationAttributeName(int specId, String newAttName) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "update specifications set attribute_name = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newAttName);
            preparedStatement.setString(2, LoggedInUser.getCurrentUser().getUserName());
            preparedStatement.setInt(3, specId);
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void updateProductSpecificationAttributeValue(int specId, String newAttValue) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String query = "update specifications set attribute_value = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newAttValue);
            preparedStatement.setString(2, LoggedInUser.getCurrentUser().getUserName());
            preparedStatement.setInt(3, specId);
            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
