package ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts;

import login.UserCredential;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdateProductSpecifications {
    Connection con;
    Scanner sc;
    int id;

    public UpdateProductSpecifications(Connection con, Scanner sc, int id) {
        this.con = con;
        this.sc = sc;
        this.id = id;
    }

    public void showProductSpecifications() {
        try {
            String query = "select * from specifications where product_id = " + id;
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No specifications are present for the given product.");
                System.exit(0);
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " Product ID: " + rs.getInt(2) +
                        " Attribute Name: " + rs.getString(3) + " Attribute Value: " + rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateProductSpecifications() {
        showProductSpecifications();
        System.out.println("Please enter id of specification you want to update. Leave blank if don't want to modify it.");
        int specId = sc.nextInt();
        sc.nextLine();

        if (String.valueOf(specId).trim().length() == 0) {
            return;
        }

        try {
            String query = "select * from specifications where id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, specId);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                System.out.println("Please enter valid specification id.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Please enter new attribute name. Leave blank if don't want to modify it.");
        String newAttName = sc.nextLine();
        if (newAttName.trim().length() != 0) {
            try {
                UserCredential userCredential = new UserCredential();
                String query = "update specifications set attribute_name = ?, updated_at = default, updated_by = ? where id = ?";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                preparedStatement.setString(1, newAttName);
                preparedStatement.setString(2, userCredential.getUsername());
                preparedStatement.setInt(3, specId);
                preparedStatement.executeUpdate();
                System.out.println("Successfully updated !!!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

            System.out.println("Please enter new attribute value. Leave blank if don't want to modify it.");
            String newAttValue = sc.nextLine();
            if (newAttValue.trim().length() != 0) {
                try {
                    UserCredential userCredential = new UserCredential();
                    String query = "update specifications set attribute_value = ?, updated_at = default, updated_by = ? where id = ?";
                    PreparedStatement preparedStatement = con.prepareStatement(query);
                    preparedStatement.setString(1, newAttValue);
                    preparedStatement.setString(2, userCredential.getUsername());
                    preparedStatement.setInt(3, specId);
                    preparedStatement.executeUpdate();
                    System.out.println("Successfully updated !!!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

