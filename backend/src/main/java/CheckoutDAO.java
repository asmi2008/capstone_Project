import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CheckoutDAO {

    public void checkout(int userId) {

        try (Connection con = DatabaseConnection.getConnection()) {

            // Transaction start
            con.setAutoCommit(false);

            double totalAmount = 0;

            // Store cart items temporarily
            List<Integer> productIds = new ArrayList<>();
            List<Integer> quantities = new ArrayList<>();
            List<Double> prices = new ArrayList<>();

            // Get cart items and product price
            String cartSql =
                    "SELECT c.ProductId, c.Quantity, p.Price " +
                    "FROM dbo.CartItems c " +
                    "JOIN dbo.Products p ON c.ProductId = p.Id " +
                    "WHERE c.UserId = ?";

            try (PreparedStatement ps = con.prepareStatement(cartSql)) {

                ps.setInt(1, userId);

                ResultSet rs = ps.executeQuery();

                while (rs.next()) {

                    int productId = rs.getInt("ProductId");
                    int quantity = rs.getInt("Quantity");
                    double price = rs.getDouble("Price");

                    double itemTotal = price * quantity;

                    totalAmount = totalAmount + itemTotal;

                    productIds.add(productId);
                    quantities.add(quantity);
                    prices.add(price);

                    System.out.println(
                            "Product ID: " + productId +
                            ", Quantity: " + quantity +
                            ", Price: " + price +
                            ", Total: " + itemTotal
                    );
                }
            }

            // Check cart
            if (productIds.isEmpty()) {
                System.out.println("Cart is empty!");
                con.rollback();
                return;
            }

            // Create Order
            String orderSql =
                    "INSERT INTO dbo.Orders " +
                    "(UserId, TotalAmount, Status) " +
                    "VALUES (?, ?, ?)";

            int orderId;

            try (PreparedStatement ps =
                         con.prepareStatement(
                                 orderSql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                ps.setInt(1, userId);
                ps.setDouble(2, totalAmount);
                ps.setString(3, "Confirmed");

                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();

                if (keys.next()) {
                    orderId = keys.getInt(1);
                } else {
                    System.out.println("Order ID not generated!");
                    con.rollback();
                    return;
                }
            }

            // Insert Order Items
            String orderItemSql =
                    "INSERT INTO dbo.OrderItems " +
                    "(OrderId, ProductId, Quantity, Price) " +
                    "VALUES (?, ?, ?, ?)";

            try (PreparedStatement ps =
                         con.prepareStatement(orderItemSql)) {

                for (int i = 0; i < productIds.size(); i++) {

                    ps.setInt(1, orderId);
                    ps.setInt(2, productIds.get(i));
                    ps.setInt(3, quantities.get(i));
                    ps.setDouble(4, prices.get(i));

                    ps.addBatch();
                }

                ps.executeBatch();
            }

            // Clear Cart
            String deleteSql =
                    "DELETE FROM dbo.CartItems WHERE UserId = ?";

            try (PreparedStatement ps =
                         con.prepareStatement(deleteSql)) {

                ps.setInt(1, userId);

                ps.executeUpdate();
            }

            // Commit everything
            con.commit();

            System.out.println("----------------------");
            System.out.println("Checkout successful!");
            System.out.println("Order ID: " + orderId);
            System.out.println("Total Amount: ₹" + totalAmount);
            System.out.println("Cart cleared successfully!");

        } catch (SQLException e) {

            System.out.println("Checkout failed!");
            e.printStackTrace();
        }
    }
}