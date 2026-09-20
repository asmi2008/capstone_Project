
    import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderItemDAO {

    public void addOrderItem(OrderItem item) {

        String sql = "INSERT INTO OrderItems " +
                     "(OrderId, ProductId, Quantity, Price) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.orderId);
            ps.setInt(2, item.productId);
            ps.setInt(3, item.quantity);
            ps.setDouble(4, item.price);

            ps.executeUpdate();

            System.out.println("Order item added successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to add order item!");
            e.printStackTrace();
        }
    }
}
