package dao;

import config.CloseDatabaseResources;
import config.DatabaseConfig;
import exception.DAOLayerException;
import model.Product;
import model.Specification;
import session.LoggedInUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    public static void addProduct(Product product) throws DAOLayerException {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet generatedKeys = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            con.setAutoCommit(false);

            String insertQuery = "insert into product (product_title, description, price, category_id, " +
                    "discount, brand, created_by, updated_by) " +
                    "values (?,?,?,?,?,?,?,?)";

            stmt = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, product.getProductTitle());
            stmt.setString(2, product.getProductDescription());
            stmt.setFloat(3, product.getProductPrice());
            stmt.setInt(4, product.getProductCategoryId());
            stmt.setFloat(5, product.getProductDiscount());
            stmt.setString(6, product.getProductBrand());
            stmt.setInt(7, LoggedInUser.currentUser.getUserId());
            stmt.setInt(8, LoggedInUser.currentUser.getUserId());

            stmt.executeUpdate();
            generatedKeys = stmt.getGeneratedKeys();
            int productId = -1;
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
                for (Specification specification : product.getSpecificationList()) {
                    specification.setSpecProductId(productId);
                    addSpecification(specification);
                }
            }

        } catch (Exception e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw new DAOLayerException("Exception occurred while adding product.", e);
        } finally {
            if (con != null) {
                try {
                    con.commit();
                    con.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                CloseDatabaseResources.closeResultSet(generatedKeys);
                CloseDatabaseResources.closePreparedStatement(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addSpecification(Specification specification) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into specifications (product_id, attribute_name, " +
                    "attribute_value, created_by, updated_by) " +
                    "values (?,?,?,?,?)";
            stmt = con.prepareStatement(insertQuery);

            stmt.setInt(1, specification.getSpecProductId());
            stmt.setString(2, specification.getSpecAttributeName());
            stmt.setString(3, specification.getSpecAttributeValue());
            stmt.setInt(4, LoggedInUser.currentUser.getUserId());
            stmt.setInt(5, LoggedInUser.currentUser.getUserId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding specifications.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkIfProductsExists() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            con = DatabaseConfig.getInstance().getConnection();
            String countQuery = "select COUNT(*) from product where is_delete = false";
            stmt = con.prepareStatement(countQuery);
            resultSet = stmt.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while checking products.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Product> getALlProducts() throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from product where is_delete = false and false = (select is_delete from category where id = category_id)";
            stmt = con.prepareStatement(selectQuery,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery();

            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt(1));
                product.setProductTitle(resultSet.getString(2));
                product.setProductDescription(resultSet.getString(3));
                product.setProductPrice(resultSet.getFloat(4));
                product.setProductCategoryId(resultSet.getInt(5));
                product.setProductDiscount(resultSet.getFloat(6));
                product.setProductBrand(resultSet.getString(7));
                product.setDelete(resultSet.getBoolean(8));
                product.setCreatedOn(resultSet.getTimestamp(9).toLocalDateTime());
                product.setUpdatedOn(resultSet.getTimestamp(10).toLocalDateTime());
                product.setCreatedBy(resultSet.getInt(11));
                product.setUpdatedBy(resultSet.getInt(12));
                productList.add(product);
            }
            return productList;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while getting products.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Product> getProductsByCategory(int id) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from product where category_id = ? and is_delete = false";
            stmt = con.prepareStatement(selectQuery,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setInt(1, id);
            resultSet = stmt.executeQuery();

            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt(1));
                product.setProductTitle(resultSet.getString(2));
                product.setProductDescription(resultSet.getString(3));
                product.setProductPrice(resultSet.getFloat(4));
                product.setProductCategoryId(resultSet.getInt(5));
                product.setProductDiscount(resultSet.getFloat(6));
                product.setProductBrand(resultSet.getString(7));
                product.setDelete(resultSet.getBoolean(8));
                product.setCreatedOn(resultSet.getTimestamp(9).toLocalDateTime());
                product.setUpdatedOn(resultSet.getTimestamp(10).toLocalDateTime());
                product.setCreatedBy(resultSet.getInt(11));
                product.setUpdatedBy(resultSet.getInt(12));
                productList.add(product);
            }
            return productList;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while getting products.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Specification> getProductSpecifications(int productId) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from specifications where product_id =  ?";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, productId);
            resultSet = stmt.executeQuery();

            List<Specification> attributeSet = new ArrayList<>();
            while (resultSet.next()) {
                Specification specification = new Specification();
                specification.setSpecId(resultSet.getInt(1));
                specification.setSpecProductId(resultSet.getInt(2));
                specification.setSpecAttributeName(resultSet.getString(3));
                specification.setSpecAttributeValue(resultSet.getString(4));
                specification.setCreatedOn(resultSet.getTimestamp(5).toLocalDateTime());
                specification.setUpdatedOn(resultSet.getTimestamp(6).toLocalDateTime());
                specification.setCreatedBy(resultSet.getInt(7));
                specification.setUpdatedBy(resultSet.getInt(8));
                attributeSet.add(specification);
            }
            return attributeSet;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching product specifications.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductBrand(int id, String newBrand) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set brand = ?, updated_at = default, updated_by = ? where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newBrand);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductDiscount(int id, float newDiscount) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set discount = ?, updated_at = default, updated_by = ?  where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setFloat(1, newDiscount);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductTitle(int id, String newTitle) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set product_title = ?, updated_at = default, updated_by = ?  where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newTitle);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductDescription(int id, String newDescription) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set description = ?, updated_at = default, updated_by = ? where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newDescription);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductPrice(int id, float newPrice) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set price = ?, updated_at = default, updated_by = ?  where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setFloat(1, newPrice);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkIfProductExists(int id) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select COUNT(id) from product where id = ? and is_delete = false and false = (select is_delete from category where id = category_id)";
            stmt = con.prepareStatement(selectQuery);
            stmt.setInt(1, id);

            resultSet = stmt.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            return count > 0;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while checking product's existence.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Product getProductById(int productId) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Product product = new Product();
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from product where id = ? and is_delete = false and false = (select is_delete from category where id = category_id)";
            preparedStatement = con.prepareStatement(selectQuery);
            preparedStatement.setInt(1, productId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                product.setProductId(resultSet.getInt(1));
                product.setProductTitle(resultSet.getString(2));
                product.setProductDescription(resultSet.getString(3));
                product.setProductPrice(resultSet.getFloat(4));
                product.setProductCategoryId(resultSet.getInt(5));
                product.setProductDiscount(resultSet.getFloat(6));
                product.setProductBrand(resultSet.getString(7));
                product.setDelete(resultSet.getBoolean(8));
                product.setCreatedOn(resultSet.getTimestamp(9).toLocalDateTime());
                product.setUpdatedOn(resultSet.getTimestamp(10).toLocalDateTime());
                product.setCreatedBy(resultSet.getInt(11));
                product.setUpdatedBy(resultSet.getInt(12));
            }

            product.setSpecificationList(getAllProductSpecifications(productId));
            return product;
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductCategory(int id, int newId) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set category_id = ?, updated_at = default, updated_by = ? where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setInt(1, newId);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkIfProductSpecificationsExistsForGivenProduct(int id) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select COUNT(*) from specifications where product_id = " + id;
            stmt = con.prepareStatement(selectQuery,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching product specifications.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Specification> getAllProductSpecifications(int id) throws DAOLayerException {
        Connection con;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select * from specifications where product_id = " + id;
            stmt = con.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            resultSet = stmt.executeQuery();
            List<Specification> specifications = new ArrayList<>();

            while (resultSet.next()) {
                Specification specification = new Specification();
                specification.setSpecId(resultSet.getInt(1));
                specification.setSpecProductId(resultSet.getInt(2));
                specification.setSpecAttributeName(resultSet.getString(3));
                specification.setSpecAttributeValue(resultSet.getString(4));
                specification.setCreatedOn(resultSet.getTimestamp(5).toLocalDateTime());
                specification.setUpdatedOn(resultSet.getTimestamp(6).toLocalDateTime());
                specification.setCreatedBy(resultSet.getInt(7));
                specification.setUpdatedBy(resultSet.getInt(8));
                specifications.add(specification);
            }

            return specifications;

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while fetching product specifications.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(stmt);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkIfSpecificationExists(int specId, int prdoductId) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select COUNT(*) from specifications where id = ? and product_id = ?";
            preparedStatement = con.prepareStatement(selectQuery,
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            preparedStatement.setInt(1, specId);
            preparedStatement.setInt(2, prdoductId);
            resultSet = preparedStatement.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            return count > 0;

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while checking existence of specification.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean checkIfProductSpecificationExists(int specId) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String selectQuery = "select COUNT(*) from specifications where id = ?";
            preparedStatement = con.prepareStatement(selectQuery);
            preparedStatement.setInt(1, specId);
            resultSet = preparedStatement.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            return count > 0;

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while checking product specifications.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
                CloseDatabaseResources.closeResultSet(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductSpecificationAttributeName(int specId, String newAttName) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update specifications set attribute_name = ?, updated_at = default, updated_by = ? where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newAttName);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, specId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateProductSpecificationAttributeValue(int specId, String newAttValue) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update specifications set attribute_value = ?, updated_at = default, updated_by = ? where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newAttValue);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, specId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

   /* public static void updateProduct(int productId, String columnName, String newValue) throws DAOLayerException{
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set " + columnName + "= ?, updated_at = default , updated_by = ? where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, newValue);
            preparedStatement.setInt(2, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(3, productId);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    public static void updateProduct(Product product) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String updateQuery = "update product set product_title = ?, description = ?, price = ?, category_id = ?, discount = ?, brand = ?, updated_at = default, updated_by = ? where id = ?";
            preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setString(1, product.getProductTitle());
            preparedStatement.setString(2, product.getProductDescription());
            preparedStatement.setFloat(3, product.getProductPrice());
            preparedStatement.setInt(4, product.getProductCategoryId());
            preparedStatement.setFloat(5, product.getProductDiscount());
            preparedStatement.setString(6, product.getProductBrand());
            preparedStatement.setInt(7, LoggedInUser.getCurrentUser().getUserId());
            preparedStatement.setInt(8, product.getProductId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while updating product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteProduct(int productId) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String deleteQuery = "update product set is_delete = true, updated_by = ?, updated_at = default where id = ?";
            preparedStatement = con.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, LoggedInUser.currentUser.getUserId());
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while deleting product.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addNewSpecification(Specification specification) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String insertQuery = "insert into specifications (product_id, attribute_name, attribute_value, created_by, updated_by) values (?,?,?,?,?)";
            preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setInt(1, specification.getSpecProductId());
            preparedStatement.setString(2, specification.getSpecAttributeName());
            preparedStatement.setString(3, specification.getSpecAttributeValue());
            preparedStatement.setInt(4, specification.getCreatedBy());
            preparedStatement.setInt(5, specification.getUpdatedBy());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while adding specification.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteProductSpecification(int specId) throws DAOLayerException {
        Connection con;
        PreparedStatement preparedStatement = null;
        try {
            con = DatabaseConfig.getInstance().getConnection();
            String deleteQuery = "delete from specifications where id = ?";
            preparedStatement = con.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, specId);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new DAOLayerException("Exception occurred while deleting specification.", e);
        } finally {
            try {
                CloseDatabaseResources.closePreparedStatement(preparedStatement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
