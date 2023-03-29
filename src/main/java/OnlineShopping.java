import java.sql.*;
import java.util.Scanner;

public class OnlineShopping {
    private static final String  HOST = "jdbc:mysql://localhost:3306/onlineshopping";
    private static  final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(HOST, USERNAME, PASSWORD);

            System.out.println("Please choose one option: ");
            System.out.println("1: Category Management");
            System.out.println("2: Product Management");
            int n = sc.nextInt();
            sc.nextLine();

            switch (n) {
                case 1:
                    handleCategoryManagement(con, sc);
                    break;
                case 2:
                    handleProductManagement(con, sc);
                    break;
                default:
                    System.out.println("Enter valid input.");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleCategoryManagement(Connection con, Scanner sc) {
        try {
            System.out.println("Please choose one option: ");
            System.out.println("1. View");
            System.out.println("2. Add");
            System.out.println("3. Update");
            int x = sc.nextInt();
            sc.nextLine();

            switch (x) {
                case 1:
                    viewCategories(con);
                    break;
                case 2:
                    addCategory(con, sc);
                    break;
                case 3:
                    updateCategory(con, sc);
                    break;
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleProductManagement(Connection con, Scanner sc) {
        try {
            System.out.println("Please choose one option: ");
            System.out.println("1. View");
            System.out.println("2. Add");
            System.out.println("3. Update");
            int x = sc.nextInt();
            sc.nextLine();

            switch (x) {
                case 1:
                    viewProducts(con);
                    break;
                case 2:
                    addProduct(con, sc);
                    break;
                case 3:
                    updateProduct(con, sc);
                    break;
                default:
                    System.out.println("Please enter valid input.");
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewCategories(Connection con) {
        try {
            String query = "select * from category";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No categories found.");
                con.close();
                return;
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " CATEGORY: " + rs.getString(2));
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addCategory(Connection con, Scanner sc) {
        try {
            System.out.println("Please enter category you want to add: ");
            String query = "insert into category (category_name) values (?)";
            String category = sc.nextLine();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, category);
            stmt.executeUpdate();
            System.out.println("Successfully inserted !!!");
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateCategory(Connection con, Scanner sc) {
        try {
            String query = "select * from category";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No record is present.");
                con.close();
                return;
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " CATEGORY: " + rs.getString(2));
            }
            System.out.println("Enter id of category you want to update: ");
            int y = sc.nextInt();
            sc.nextLine();
            String query2 = "select id from category where id = ?";
            PreparedStatement stmt2 = con.prepareStatement(query2);
            stmt2.setInt(1, y);

            ResultSet rss = stmt2.executeQuery();
            if (!rss.next()) {
                System.out.println("ID not found.");
            } else {
                System.out.println("Enter updated category name: ");
                String newCategory = sc.nextLine();
                String query3 = "update category set category_name = ? where id =" + y;
                PreparedStatement stmt3 = con.prepareStatement(query3);
                stmt3.setString(1, newCategory);
                stmt3.executeUpdate();
                System.out.println("Successfully updated !!!");
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewProducts(Connection con) {
        try {
            String query = "select * from product";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                System.out.println("No products found.");
                con.close();
                return;
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " PRODUCT_TITLE: " + rs.getString(2) + " DESCRIPTIPON: " + rs.getString(3) + " PRICE: " + rs.getInt(4) + " CATEGORY_ID: " + rs.getInt(5));
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addProduct(Connection con, Scanner sc) {
        try {
            String catQuery = "select * from category";
            PreparedStatement stmt = con.prepareStatement(catQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No categories are present. Kindly add one before.");
                con.close();
                return;
            }

            String query = "insert into product (product_title, description, price, category_id) values (?,?,?,?)";
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

            PreparedStatement stmt2 = con.prepareStatement(query);
            stmt2.setString(1, product_title);
            stmt2.setString(2, description);
            stmt2.setFloat(3, price);
            stmt2.setInt(4, category);
            stmt2.executeUpdate();
            System.out.println("Successfully inserted !!!");
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateProduct(Connection con, Scanner sc) {
        try {
            String query = "select * from product";
            PreparedStatement stmt = con.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("No record is present.");
                con.close();
                return;
            }

            rs.previous();
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " PRODUCT_TITLE: " + rs.getString(2) + " DESCRIPTIPON: " + rs.getString(3) + " PRICE: " + rs.getInt(4) + " CATEGORY_ID: " + rs.getInt(5));
            }
            System.out.println("Enter id of product whose price you want to update: ");
            int y = sc.nextInt();
            sc.nextLine();
            String query2 = "select id from product where id = ?";
            PreparedStatement stmt2 = con.prepareStatement(query2);
            stmt2.setInt(1, y);

            ResultSet rss = stmt2.executeQuery();
            if (!rss.next()) {
                System.out.println("ID not found.");
            } else {
                System.out.println("Enter updated product price: ");
                float newPrice = sc.nextFloat();
                String query3 = "update product set price = ? where id =" + y;
                PreparedStatement stmt3 = con.prepareStatement(query3);
                stmt3.setFloat(1, newPrice);
                stmt3.executeUpdate();
                System.out.println("Successfully updated !!!");
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
