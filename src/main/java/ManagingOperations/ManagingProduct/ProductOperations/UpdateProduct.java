package ManagingOperations.ManagingProduct.ProductOperations;

import ManagingOperations.ManagingProduct.ProductOperations.UpdateProducts.*;
import config.DatabaseConfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class UpdateProduct {
    private Connection con = DatabaseConfig.getConnection();
    Scanner sc = new Scanner(System.in);

    public void updateProduct() {
        try {
            String query = "select * from product";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No product is present.");
                con.close();
                return;
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("PROUDCT_ID: " + rs.getInt(1) + " PRODUCT_TITLE: " + rs.getString(2) + " PRICE: " + rs.getFloat(4) + " CATEGORY_ID: " + rs.getInt(5)
                        + " DISCOUNT: " + rs.getString(6) + " BRAND: " + rs.getString(7));
            }

            System.out.println("Enter id of product you want to update: ");
            int y = sc.nextInt();
            sc.nextLine();
            String query2 = "select id from product where id = ?";
            PreparedStatement stmt2 = con.prepareStatement(query2);
            stmt2.setInt(1, y);

            ResultSet rss = stmt2.executeQuery();
            if (!rss.next()) {
                System.out.println("ID not found.");
            } else {
                System.out.println("Enter choice of attribute you want to update: ");
                System.out.println("1. Product Title.");
                System.out.println("2. Description.");
                System.out.println("3. Price.");
                System.out.println("4. Category.");
                System.out.println("5. Discount.");
                System.out.println("6. Brand.");
                System.out.println("7. Specifications.");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        UpdateProductTitle updateProductTitle = new UpdateProductTitle(con, sc, y);
                        updateProductTitle.updateProductTitle();
                        break;
                    case 2:
                        UpdateProductDescription updateProductDescription = new UpdateProductDescription(con, sc, y);
                        updateProductDescription.updateProductDescription();
                        break;
                    case 3:
                        UpdateProductPrice updateProductPrice = new UpdateProductPrice(con, sc, y);
                        updateProductPrice.updateProductPrice();
                        break;
                    case 4:
                        UpdateProductCategory updateProductCategory = new UpdateProductCategory(con, sc, y);
                        updateProductCategory.updateProductCategory();
                        break;
                    case 5:
                        UpdateProductDiscount updateProductDiscount = new UpdateProductDiscount(con, sc, y);
                        updateProductDiscount.updateProductDiscount();
                        break;
                    case 6:
                        UpdateProductBrand updateProductBrand = new UpdateProductBrand(con, sc, y);
                        updateProductBrand.updateProductBrand();
                        break;
                    case 7:
                        UpdateProductSpecifications updateProductSpecifications = new UpdateProductSpecifications(con, sc, y);
                        updateProductSpecifications.updateProductSpecifications();
                        break;
                    default:
                        System.out.println("Please enter valid input.");
                        break;
                }

            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
