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

@WebServlet("/admin-users")
public class AdminUserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        // Admin login check
        if (session == null ||
            !"Admin".equals(session.getAttribute("role"))) {

            response.getWriter().println(
                "<html>" +
                "<head>" +
                "<title>Admin Login Required</title>" +
                "<style>" +
                "body{" +
                "font-family:Arial,sans-serif;" +
                "background:#f5f7fb;" +
                "display:flex;" +
                "justify-content:center;" +
                "align-items:center;" +
                "height:100vh;" +
                "}" +
                ".message{" +
                "background:white;" +
                "padding:40px;" +
                "border-radius:15px;" +
                "box-shadow:0 8px 25px rgba(0,0,0,0.12);" +
                "text-align:center;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='message'>" +
                "<h2>Admin Login Required!</h2>" +
                "<p>Please login as an administrator.</p>" +
                "</div>" +
                "</body>" +
                "</html>"
            );

            return;
        }

        response.getWriter().println(

            "<!DOCTYPE html>" +
            "<html>" +

            "<head>" +

            "<meta charset='UTF-8'>" +

            "<meta name='viewport' " +
            "content='width=device-width, initial-scale=1.0'>" +

            "<title>Admin Users - Asmi Mart</title>" +

            "<style>" +

            "*{" +
            "box-sizing:border-box;" +
            "margin:0;" +
            "padding:0;" +
            "font-family:Arial,sans-serif;" +
            "}" +

            "body{" +
            "background:#f4f6fb;" +
            "color:#333;" +
            "min-height:100vh;" +
            "}" +

            /* Header */

            ".header{" +
            "background:linear-gradient(135deg,#667eea,#764ba2);" +
            "color:white;" +
            "padding:22px 45px;" +
            "display:flex;" +
            "justify-content:space-between;" +
            "align-items:center;" +
            "box-shadow:0 4px 15px rgba(0,0,0,0.15);" +
            "}" +

            ".brand{" +
            "font-size:27px;" +
            "font-weight:bold;" +
            "}" +

            ".admin-label{" +
            "font-size:14px;" +
            "background:rgba(255,255,255,0.18);" +
            "padding:8px 15px;" +
            "border-radius:20px;" +
            "}" +

            /* Main */

            ".container{" +
            "width:92%;" +
            "max-width:1200px;" +
            "margin:35px auto;" +
            "}" +

            ".page-title{" +
            "display:flex;" +
            "justify-content:space-between;" +
            "align-items:center;" +
            "margin-bottom:25px;" +
            "}" +

            ".page-title h1{" +
            "font-size:30px;" +
            "color:#222;" +
            "}" +

            ".page-title p{" +
            "color:#777;" +
            "margin-top:7px;" +
            "}" +

            /* Card */

            ".table-card{" +
            "background:white;" +
            "border-radius:16px;" +
            "padding:25px;" +
            "box-shadow:0 6px 25px rgba(0,0,0,0.08);" +
            "overflow-x:auto;" +
            "}" +

            ".table-title{" +
            "font-size:20px;" +
            "font-weight:bold;" +
            "margin-bottom:20px;" +
            "color:#333;" +
            "}" +

            /* Table */

            "table{" +
            "width:100%;" +
            "border-collapse:collapse;" +
            "min-width:650px;" +
            "}" +

            "th{" +
            "background:#667eea;" +
            "color:white;" +
            "padding:15px;" +
            "text-align:left;" +
            "font-size:14px;" +
            "}" +

            "th:first-child{" +
            "border-radius:8px 0 0 8px;" +
            "}" +

            "th:last-child{" +
            "border-radius:0 8px 8px 0;" +
            "}" +

            "td{" +
            "padding:15px;" +
            "border-bottom:1px solid #eee;" +
            "font-size:14px;" +
            "}" +

            "tr:hover{" +
            "background:#f7f8ff;" +
            "}" +

            ".id-badge{" +
            "background:#eef0ff;" +
            "color:#667eea;" +
            "padding:6px 10px;" +
            "border-radius:7px;" +
            "font-weight:bold;" +
            "}" +

            ".role{" +
            "display:inline-block;" +
            "padding:6px 12px;" +
            "border-radius:20px;" +
            "font-size:12px;" +
            "font-weight:bold;" +
            "background:#e8f5e9;" +
            "color:#2e7d32;" +
            "}" +

            /* Navigation */

            ".navigation{" +
            "display:flex;" +
            "gap:15px;" +
            "margin-top:25px;" +
            "flex-wrap:wrap;" +
            "}" +

            ".nav-button{" +
            "text-decoration:none;" +
            "padding:12px 20px;" +
            "border-radius:8px;" +
            "font-weight:bold;" +
            "font-size:14px;" +
            "transition:0.2s;" +
            "}" +

            ".products{" +
            "background:#667eea;" +
            "color:white;" +
            "}" +

            ".orders{" +
            "background:#764ba2;" +
            "color:white;" +
            "}" +

            ".nav-button:hover{" +
            "opacity:0.85;" +
            "transform:translateY(-1px);" +
            "}" +

            /* Footer */

            ".footer{" +
            "text-align:center;" +
            "color:#888;" +
            "font-size:13px;" +
            "margin:35px 0;" +
            "}" +

            /* Mobile */

            "@media(max-width:700px){" +

            ".header{" +
            "padding:18px 20px;" +
            "}" +

            ".brand{" +
            "font-size:22px;" +
            "}" +

            ".container{" +
            "width:94%;" +
            "margin:25px auto;" +
            "}" +

            ".page-title h1{" +
            "font-size:24px;" +
            "}" +

            ".table-card{" +
            "padding:15px;" +
            "}" +

            "}" +

            "</style>" +

            "</head>" +

            "<body>" +

            /* Header */

            "<div class='header'>" +

            "<div class='brand'>Asmi Mart</div>" +

            "<div class='admin-label'>⚙️ Admin Panel</div>" +

            "</div>" +

            /* Main */

            "<div class='container'>" +

            "<div class='page-title'>" +

            "<div>" +
            "<h1>👥 Users Management</h1>" +
            "<p>View and manage registered users</p>" +
            "</div>" +

            "</div>" +

            "<div class='table-card'>" +

            "<div class='table-title'>All Registered Users</div>" +

            "<table>" +

            "<tr>" +
            "<th>ID</th>" +
            "<th>Name</th>" +
            "<th>Email</th>" +
            "<th>Role</th>" +
            "</tr>"
        );

        String sql =
            "SELECT Id, Name, Email, Role " +
            "FROM Users " +
            "ORDER BY Id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                response.getWriter().println(

                    "<tr>" +

                    "<td>" +
                    "<span class='id-badge'>" +
                    rs.getInt("Id") +
                    "</span>" +
                    "</td>" +

                    "<td>" +
                    rs.getString("Name") +
                    "</td>" +

                    "<td>" +
                    rs.getString("Email") +
                    "</td>" +

                    "<td>" +
                    "<span class='role'>" +
                    rs.getString("Role") +
                    "</span>" +
                    "</td>" +

                    "</tr>"
                );
            }

        } catch (SQLException e) {

            response.getWriter().println(

                "<tr>" +
                "<td colspan='4' " +
                "style='text-align:center;color:red;padding:25px;'>" +
                "Failed to load users!" +
                "</td>" +
                "</tr>"
            );

            e.printStackTrace();
        }

        response.getWriter().println(

            "</table>" +

            "</div>" +

            /* Navigation */

            "<div class='navigation'>" +

            "<a href='admin-products' " +
            "class='nav-button products'>" +
            "📦 Admin Products" +
            "</a>" +

            "<a href='admin-orders' " +
            "class='nav-button orders'>" +
            "🛒 Admin Orders" +
            "</a>" +

            "</div>" +

            "<div class='footer'>" +
            "Asmi Mart Admin Panel © 2026" +
            "</div>" +

            "</div>" +

            "</body>" +

            "</html>"
        );
    }
}