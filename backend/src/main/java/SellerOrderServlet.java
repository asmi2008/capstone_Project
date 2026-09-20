 import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/seller-orders")
public class SellerOrderServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        // Get current session
        HttpSession session = request.getSession(false);

        // Check Seller Login
        if (session == null ||
            !"Seller".equals(session.getAttribute("role"))) {

            response.getWriter().println(
                "<h2>Seller Login Required!</h2>"
            );

            return;
        }

        // Current Seller ID
        int sellerId = 11;

        String sql =
            "SELECT o.Id AS OrderId, " +
            "o.UserId, " +
            "p.Name AS ProductName, " +
            "oi.Quantity, " +
            "oi.Price, " +
            "o.TotalAmount, " +
            "o.Status " +
            "FROM dbo.Orders o " +
            "JOIN dbo.OrderItems oi ON o.Id = oi.OrderId " +
            "JOIN dbo.Products p ON oi.ProductId = p.Id " +
            "WHERE p.SellerId = ? " +
            "ORDER BY o.Id DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);

            ResultSet rs = ps.executeQuery();

            response.getWriter().println(
                "<h2>Seller Orders</h2>"
            );

            boolean found = false;

            while (rs.next()) {

                found = true;

                response.getWriter().println(
                    "<hr>"
                );

                response.getWriter().println(
                    "<p>Order ID: " +
                    rs.getInt("OrderId") +
                    "</p>"
                );

                response.getWriter().println(
                    "<p>Buyer ID: " +
                    rs.getInt("UserId") +
                    "</p>"
                );

                response.getWriter().println(
                    "<p>Product: " +
                    rs.getString("ProductName") +
                    "</p>"
                );

                response.getWriter().println(
                    "<p>Quantity: " +
                    rs.getInt("Quantity") +
                    "</p>"
                );

                response.getWriter().println(
                    "<p>Product Price: " +
                    rs.getDouble("Price") +
                    "</p>"
                );

                response.getWriter().println(
                    "<p>Order Total: " +
                    rs.getDouble("TotalAmount") +
                    "</p>"
                );

                response.getWriter().println(
                    "<p>Status: " +
                    rs.getString("Status") +
                    "</p>"
                );
            }

            if (!found) {

                response.getWriter().println(
                    "<p>No orders received yet.</p>"
                );
            }

        } catch (SQLException e) {

            response.getWriter().println(
                "<h2>Failed to load seller orders!</h2>"
            );

            e.printStackTrace();
        }
    }
}
    

