import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDAO {

    // CREATE ORDER
    public void createOrder(int userId, double totalAmount) {

        String sql = "INSERT INTO Orders (UserId, TotalAmount, Status) " +
                     "VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDouble(2, totalAmount);
            ps.setString(3, "Confirmed");

            ps.executeUpdate();

            System.out.println("Order created successfully!");

        } catch (SQLException e) {
            System.out.println("Failed to create order!");
            e.printStackTrace();
        }
    }

    // VIEW ORDER HISTORY
    public void viewOrderHistory(int userId) {

        String sql = "SELECT * FROM Orders WHERE UserId = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                System.out.println("Order ID: " + rs.getInt("Id"));
                System.out.println("User ID: " + rs.getInt("UserId"));
                System.out.println("Total Amount: " + rs.getDouble("TotalAmount"));
                System.out.println("Status: " + rs.getString("Status"));

                System.out.println("----------------------");
            }

        } catch (SQLException e) {
            System.out.println("Failed to view order history!");
            e.printStackTrace();
        }
    }
}