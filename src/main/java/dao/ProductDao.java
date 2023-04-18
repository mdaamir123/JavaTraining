package dao;

import config.DatabaseConfig;
import model.Product;
import model.Specification;
import session.LoggedInUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDao {
    private static Scanner sc = new Scanner(System.in);

    public static int addProduct(Product product) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into product (product_title, description, price, category_id, discount, brand, created_by, updated_by) values (?,?,?,?,?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, product.getProductTitle());
            stmt.setString(2, product.getProductDescription());
            stmt.setFloat(3, product.getProductPrice());
            stmt.setInt(4, product.getProductCategoryId());
            stmt.setFloat(5, product.getProductDiscount());
            stmt.setString(6, product.getProductBrand());
            stmt.setInt(7, LoggedInUser.currentUser.getUserId());
            stmt.setInt(8, LoggedInUser.currentUser.getUserId());

            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int productId = -1;
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            }
            stmt.close();
            return productId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void addSpecification(Specification specification) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into specifications (product_id, attribute_name, attribute_value, created_by, updated_by) values (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(insertQuery);

            stmt.setInt(1, specification.getSpecProductId());
            stmt.setString(2, specification.getSpecAttributeName());
            stmt.setString(3, specification.getSpecAttributeValue());
            stmt.setInt(4, LoggedInUser.currentUser.getUserId());
            stmt.setInt(5, LoggedInUser.currentUser.getUserId());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static int getLastInsertedProductId() {
//        try {
//            Connection con = DatabaseConfig.getInstance().getConnection();
//            String getId = "select id from product order by id desc limit 1";
//            PreparedStatement ps = con.prepareStatement(getId);
//            ResultSet idSet = ps.executeQuery();
//            int product_id = 0;
//            while (idSet.next()) {
//                product_id = idSet.getInt(1);
//            }
//
//            ps.close();
//            idSet.close();
//            return product_id;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }

    public static boolean checkIfProductsExists() {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String countQuery = "select COUNT(*) from product";
            PreparedStatement stmt = con.prepareStatement(countQuery);
            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            stmt.close();
            rs.close();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static List<Product> getALlProducts() {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from product";
            PreparedStatement stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
                product.setCreatedBy(rs.getInt(10));
                product.setUpdatedBy(rs.getInt(11));
                resultSet.add(product);
            }

            stmt.close();
            rs.close();
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Product> getProductsByCategory(int id) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from product where category_id = " + id;
            PreparedStatement stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
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
                product.setCreatedBy(rs.getInt(10));
                product.setUpdatedBy(rs.getInt(11));
                resultSet.add(product);
            }

            stmt.close();
            rs.close();
            return resultSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Specification> getAllSpecifications() {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from specifications";
            PreparedStatement stmt = con.prepareStatement(selectQuery);
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
                specification.setCreatedBy(rs.getInt(7));
                specification.setUpdatedBy(rs.getInt(8));
                attributeSet.add(specification);
            }

            stmt.close();
            rs.close();
            return attributeSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateProductBrand(int id, String newBrand) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set brand = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newBrand);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductDiscount(int id, float newDiscount) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set discount = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setFloat(1, newDiscount);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductTitle(int id, String newTitle) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set product_title = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newTitle);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductDescription(int id, String newDescription) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set description = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newDescription);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductPrice(int id, float newPrice) {

        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set price = ?, updated_at = default, updated_by = ?  where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setFloat(1, newPrice);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfProductExists(int id) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select COUNT(id) from product where id = ?";
            PreparedStatement stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            stmt.close();
            rs.close();
            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateProductCategory(int id, int newId) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set category_id = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setInt(1, newId);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkIfProductSpecificationsExistsForGivenProduct(int id) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select COUNT(*) from specifications where product_id = " + id;
            PreparedStatement stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            stmt.close();
            rs.close();
            return count > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Specification> getAllProductSpecifications(int id) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from specifications where product_id = " + id;
            PreparedStatement stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            List<Specification> specifications = new ArrayList<>();

            while (rs.next()) {
                Specification specification = new Specification();
                specification.setSpecId(rs.getInt(1));
                specification.setSpecProductId(rs.getInt(2));
                specification.setSpecAttributeName(rs.getString(3));
                specification.setSpecAttributeValue(rs.getString(4));
                specification.setCreatedOn(rs.getTimestamp(5).toLocalDateTime());
                specification.setUpdatedOn(rs.getTimestamp(6).toLocalDateTime());
                specification.setCreatedBy(rs.getInt(7));
                specification.setUpdatedBy(rs.getInt(8));
                specifications.add(specification);
            }

            stmt.close();
            rs.close();
            return specifications;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkIfProductSpecificationExists(int specId) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select COUNT(*) from specifications where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(selectQuery);
            preparedStatement.setInt(1, specId);
            ResultSet rs = preparedStatement.executeQuery();

            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }

            preparedStatement.close();
            rs.close();
            return count > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateProductSpecificationAttributeName(int specId, String newAttName) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update specifications set attribute_name = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newAttName);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, specId);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProductSpecificationAttributeValue(int specId, String newAttValue) {
        try {
            Connection con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update specifications set attribute_value = ?, updated_at = default, updated_by = ? where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newAttValue);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, specId);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
