import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // ADD PRODUCT TO CART
    public void addToCart(int userId, int productId, int quantity) {

        String sql = "INSERT INTO CartItems (UserId, ProductId, Quantity) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);

            ps.executeUpdate();

            System.out.println("Product added to cart successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add product to cart!");
            e.printStackTrace();
        }
    }

    // VIEW CART
    public List<CartItem> getCartItems(int userId) {

        List<CartItem> cartItems = new ArrayList<>();

        String sql = "SELECT * FROM CartItems WHERE UserId = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                CartItem item = new CartItem(
                    rs.getInt("Id"),
                    rs.getInt("UserId"),
                    rs.getInt("ProductId"),
                    rs.getInt("Quantity")
                );

                cartItems.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Failed to view cart!");
            e.printStackTrace();
        }

        return cartItems;
    }

    // REMOVE PRODUCT FROM CART
    public void removeFromCart(int id) {

        String sql = "DELETE FROM CartItems WHERE Id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Product removed from cart!");

        } catch (SQLException e) {
            System.out.println("Failed to remove product from cart!");
            e.printStackTrace();
        }
    }

    // UPDATE QUANTITY
    public void updateQuantity(int id, int quantity) {

        String sql = "UPDATE CartItems SET Quantity = ? WHERE Id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, quantity);
            ps.setInt(2, id);

            ps.executeUpdate();

            System.out.println("Cart quantity updated!");

        } catch (SQLException e) {
            System.out.println("Failed to update cart quantity!");
            e.printStackTrace();
        }
    }

    // VIEW CART TOTAL PRICE
    public double getCartTotal(int userId) {

        double total = 0;

        String sql =
                "SELECT SUM(p.Price * c.Quantity) AS Total " +
                "FROM CartItems c " +
                "JOIN Products p ON c.ProductId = p.Id " +
                "WHERE c.UserId = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("Total");
            }

            System.out.println("Cart Total: ₹" + total);

        } catch (SQLException e) {
            System.out.println("Failed to calculate cart total!");
            e.printStackTrace();
        }

        return total;
    }
}