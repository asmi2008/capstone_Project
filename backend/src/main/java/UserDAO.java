import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // ADD USER
    public boolean addUser(User user) {

        // Check whether email already exists
        if (emailExists(user.email)) {
            System.out.println("Email already exists!");
            return false;
        }

        String sql = "INSERT INTO Users (Name, Email, Password) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.name);
            ps.setString(2, user.email);
            ps.setString(3, user.password);

            ps.executeUpdate();

            System.out.println("User added successfully!");
            return true;

        } catch (SQLException e) {
            System.out.println("Failed to add user!");
            e.printStackTrace();
            return false;
        }
    }

    // CHECK DUPLICATE EMAIL
    public boolean emailExists(String email) {

        String sql = "SELECT * FROM Users WHERE Email = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("Email check failed!");
            e.printStackTrace();
            return false;
        }
    }

    // CHECK LOGIN
    public boolean checkLogin(String email, String password) {

        String sql =
                "SELECT * FROM Users " +
                "WHERE Email = ? AND Password = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("Login failed!");
            e.printStackTrace();
            return false;
        }
    }

    // CHECK SELLER LOGIN
    public boolean checkSellerLogin(String email, String password) {

        String sql =
                "SELECT * FROM Users " +
                "WHERE Email = ? AND Password = ? AND Role = 'Seller'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("Seller login successful!");
                return true;

            } else {

                System.out.println("Invalid seller email or password!");
                return false;
            }

        } catch (SQLException e) {

            System.out.println("Seller login failed!");
            e.printStackTrace();
            return false;
        }
    }

    // GET USER ID BY EMAIL
    public int getUserId(String email) {

        String sql =
                "SELECT Id FROM Users WHERE Email = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return rs.getInt("Id");
            }

        } catch (SQLException e) {

            System.out.println("Failed to get user ID!");
            e.printStackTrace();
        }

        return -1;
    }
}