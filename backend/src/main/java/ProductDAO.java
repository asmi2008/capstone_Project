import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // ADD PRODUCT
    public void addProduct(Product product) {

        String sql = "INSERT INTO Products " +
                     "(Name, Description, Price, Category, Stock, SellerId) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.name);
            ps.setString(2, product.description);
            ps.setDouble(3, product.price);
            ps.setString(4, product.category);
            ps.setInt(5, product.stock);
            ps.setInt(6, product.sellerId);

            ps.executeUpdate();

            System.out.println("Product added successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add product!");
            e.printStackTrace();
        }
    }

    // UPDATE PRODUCT
    public void updateProduct(Product product) {

        String sql = "UPDATE Products SET Name = ?, Description = ?, " +
                     "Price = ?, Category = ?, Stock = ? " +
                     "WHERE Id = ? AND SellerId = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.name);
            ps.setString(2, product.description);
            ps.setDouble(3, product.price);
            ps.setString(4, product.category);
            ps.setInt(5, product.stock);
            ps.setInt(6, product.id);
            ps.setInt(7, product.sellerId);

            ps.executeUpdate();

            System.out.println("Product updated successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to update product!");
            e.printStackTrace();
        }
    }

    // DELETE PRODUCT BY SELLER
    public void deleteProduct(int id, int sellerId) {

        String sql = "DELETE FROM Products " +
                     "WHERE Id = ? AND SellerId = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, sellerId);

            ps.executeUpdate();

            System.out.println("Product deleted successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to delete product!");
            e.printStackTrace();
        }
    }

    // DELETE PRODUCT BY ADMIN
    public void deleteProductByAdmin(int id) {

        String sql = "DELETE FROM Products WHERE Id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Product removed by Admin!");

        } catch (SQLException e) {
            System.out.println("Failed to remove product by Admin!");
            e.printStackTrace();
        }
    }

    // VIEW ALL PRODUCTS
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM Products";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Product product = new Product(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price"),
                    rs.getString("Category"),
                    rs.getInt("Stock"),
                    rs.getInt("SellerId")
                );

                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Failed to get products!");
            e.printStackTrace();
        }

        return products;
    }

    // SEARCH PRODUCT BY NAME
    public List<Product> searchProducts(String keyword) {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM Products WHERE Name LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product product = new Product(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price"),
                    rs.getString("Category"),
                    rs.getInt("Stock"),
                    rs.getInt("SellerId")
                );

                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Search failed!");
            e.printStackTrace();
        }

        return products;
    }

    // FILTER PRODUCTS BY CATEGORY
    public List<Product> filterByCategory(String category) {

        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM Products WHERE Category = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product product = new Product(
                    rs.getInt("Id"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price"),
                    rs.getString("Category"),
                    rs.getInt("Stock"),
                    rs.getInt("SellerId")
                );

                products.add(product);
            }

        } catch (SQLException e) {
            System.out.println("Category filter failed!");
            e.printStackTrace();
        }

        return products;
    }
}