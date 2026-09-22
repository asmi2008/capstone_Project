 
    
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

@WebServlet("/order-history")
public class OrderHistoryServlet extends HttpServlet {

protected void doGet(HttpServletRequest request,  
                     HttpServletResponse response)  
        throws ServletException, IOException {  

    response.setContentType("text/html;charset=UTF-8");  

    HttpSession session = request.getSession(false);  

    if (session == null || session.getAttribute("userId") == null) {  
        response.getWriter().println(  
            "<h2>Please login first!</h2>"  
        );  
        return;  
    }  

    int userId = (int) session.getAttribute("userId");  

    response.getWriter().println("<!DOCTYPE html>");  
    response.getWriter().println("<html>");  
    response.getWriter().println("<head>");  

    response.getWriter().println(  
        "<meta charset='UTF-8'>"  
    );  

    response.getWriter().println(  
        "<meta name='viewport' " +  
        "content='width=device-width, initial-scale=1.0'>"  
    );  

    response.getWriter().println(  
        "<title>My Orders - AsmiMart</title>"  
    );  

    response.getWriter().println("<style>");  

    response.getWriter().println(  
        "* { box-sizing: border-box; margin: 0; padding: 0; }"  
    );  

    response.getWriter().println(  
        "body {" +  
        "font-family: Arial, sans-serif;" +  
        "background: linear-gradient(135deg, #f7e9ff, #fff4f8);" +  
        "min-height: 100vh;" +  
        "color: #29203a;" +  
        "}"  
    );  

    /* NAVBAR */  

    response.getWriter().println(  
        ".navbar {" +  
        "background: linear-gradient(135deg, #6a1b9a, #8e24aa, #d81b60);" +  
        "color: white;" +  
        "padding: 18px 7%;" +  
        "display: flex;" +  
        "justify-content: space-between;" +  
        "align-items: center;" +  
        "box-shadow: 0 4px 15px rgba(0,0,0,0.15);" +  
        "}"  
    );  

    response.getWriter().println(  
        ".logo {" +  
        "font-size: 25px;" +  
        "font-weight: bold;" +  
        "letter-spacing: 1px;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".nav-links {" +  
        "display: flex;" +  
        "gap: 22px;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".nav-links a {" +  
        "color: white;" +  
        "text-decoration: none;" +  
        "font-weight: bold;" +  
        "}"  
    );  

    /* MAIN */  

    response.getWriter().println(  
        ".container {" +  
        "max-width: 950px;" +  
        "margin: 50px auto;" +  
        "padding: 20px;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".page-title {" +  
        "text-align: center;" +  
        "margin-bottom: 35px;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".page-title h1 {" +  
        "font-size: 34px;" +  
        "color: #4a126b;" +  
        "margin-bottom: 10px;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".page-title p {" +  
        "color: #777;" +  
        "font-size: 16px;" +  
        "}"  
    );  

    /* ORDER CARD */  

    response.getWriter().println(  
        ".orders-card {" +  
        "background: white;" +  
        "border-radius: 20px;" +  
        "padding: 25px;" +  
        "box-shadow: 0 10px 30px rgba(80,30,100,0.12);" +  
        "overflow-x: auto;" +  
        "}"  
    );  

    response.getWriter().println(  
        "table {" +  
        "width: 100%;" +  
        "border-collapse: collapse;" +  
        "}"  
    );  

    response.getWriter().println(  
        "th {" +  
        "background: linear-gradient(135deg, #6a1b9a, #8e24aa);" +  
        "color: white;" +  
        "padding: 16px;" +  
        "text-align: left;" +  
        "font-size: 15px;" +  
        "}"  
    );  

    response.getWriter().println(  
        "td {" +  
        "padding: 17px 16px;" +  
        "border-bottom: 1px solid #eee;" +  
        "font-size: 15px;" +  
        "}"  
    );  

    response.getWriter().println(  
        "tr:last-child td {" +  
        "border-bottom: none;" +  
        "}"  
    );  

    response.getWriter().println(  
        "tr:hover td {" +  
        "background: #fff8fc;" +  
        "}"  
    );  

    /* ORDER ID */  

    response.getWriter().println(  
        ".order-id {" +  
        "font-weight: bold;" +  
        "color: #6a1b9a;" +  
        "}"  
    );  

    /* AMOUNT */  

    response.getWriter().println(  
        ".amount {" +  
        "font-weight: bold;" +  
        "color: #d81b60;" +  
        "}"  
    );  

    /* STATUS */  

    response.getWriter().println(  
        ".status {" +  
        "display: inline-block;" +  
        "padding: 7px 15px;" +  
        "border-radius: 20px;" +  
        "background: #f3e5f5;" +  
        "color: #6a1b9a;" +  
        "font-weight: bold;" +  
        "font-size: 13px;" +  
        "}"  
    );  

    /* EMPTY */  

    response.getWriter().println(  
        ".empty {" +  
        "text-align: center;" +  
        "padding: 45px 20px;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".empty-icon {" +  
        "font-size: 55px;" +  
        "margin-bottom: 15px;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".empty h2 {" +  
        "color: #4a126b;" +  
        "margin-bottom: 10px;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".empty p {" +  
        "color: #777;" +  
        "margin-bottom: 25px;" +  
        "}"  
    );  

    /* BUTTONS */  

    response.getWriter().println(  
        ".buttons {" +  
        "display: flex;" +  
        "justify-content: center;" +  
        "gap: 15px;" +  
        "margin-top: 30px;" +  
        "flex-wrap: wrap;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".btn {" +  
        "display: inline-block;" +  
        "padding: 13px 25px;" +  
        "border-radius: 30px;" +  
        "text-decoration: none;" +  
        "font-weight: bold;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".shop {" +  
        "background: linear-gradient(135deg, #8e24aa, #d81b60);" +  
        "color: white;" +  
        "}"  
    );  

    response.getWriter().println(  
        ".cart {" +  
        "background: #f0eaf5;" +  
        "color: #5a3d68;" +  
        "}"  
    );  

    /* MOBILE */  

    response.getWriter().println(  
        "@media (max-width: 600px) {" +  
        ".navbar { padding: 16px 5%; }" +  
        ".nav-links { gap: 10px; font-size: 13px; }" +  
        ".container { margin: 30px auto; padding: 15px; }" +  
        ".page-title h1 { font-size: 27px; }" +  
        ".orders-card { padding: 12px; }" +  
        "th, td { padding: 12px 9px; font-size: 13px; }" +  
        "}"  
    );  

    response.getWriter().println("</style>");  
    response.getWriter().println("</head>");  

    response.getWriter().println("<body>");  

    /* NAVBAR */  

    response.getWriter().println(  
        "<div class='navbar'>" +  
        "<div class='logo'>AsmiMart</div>" +  
        "<div class='nav-links'>" +  
        "<a href='http://127.0.0.1:5500/frontend/home.html'>Home</a>" +  
        "<a href='http://127.0.0.1:5500/frontend/products.html'>Shop</a>" +  
        "</div>" +  
        "</div>"  
    );  

    /* CONTENT */  

    response.getWriter().println(  
        "<div class='container'>"  
    );  

    response.getWriter().println(  
        "<div class='page-title'>" +  
        "<h1>My Orders</h1>" +  
        "<p>Track and view your recent orders</p>" +  
        "</div>"  
    );  

    response.getWriter().println(  
        "<div class='orders-card'>"  
    );  

    String sql =  
        "SELECT Id, TotalAmount, Status " +  
        "FROM Orders " +  
        "WHERE UserId = ? " +  
        "ORDER BY Id DESC";  

    try (Connection con = DatabaseConnection.getConnection();  
         PreparedStatement ps = con.prepareStatement(sql)) {  

        ps.setInt(1, userId);  

        ResultSet rs = ps.executeQuery();  

        boolean hasOrders = false;  

        response.getWriter().println("<table>");  

        response.getWriter().println(  
            "<tr>" +  
            "<th>Order ID</th>" +  
            "<th>Total Amount</th>" +  
            "<th>Status</th>" +  
            "</tr>"  
        );  

        while (rs.next()) {  

            hasOrders = true;  

            int orderId = rs.getInt("Id");  
            double amount = rs.getDouble("TotalAmount");  
            String status = rs.getString("Status");  

            response.getWriter().println(  
                "<tr>" +  

                "<td class='order-id'>" +  
                "#" + orderId +  
                "</td>" +  

                "<td class='amount'>" +  
                "₹" + String.format("%.2f", amount) +  
                "</td>" +  

                "<td>" +  
                "<span class='status'>" +  
                status +  
                "</span>" +  
                "</td>" +  

                "</tr>"  
            );  
        }  

        response.getWriter().println("</table>");  

        if (!hasOrders) {  

            response.getWriter().println(  
                "<div class='empty'>" +  
                "<div class='empty-icon'>📦</div>" +  
                "<h2>No Orders Yet</h2>" +  
                "<p>Your placed orders will appear here.</p>" +  
                "<a class='btn shop' " +  
                "href='http://127.0.0.1:5500/frontend/products.html'>" +  
                "Start Shopping" +  
                "</a>" +  
                "</div>"  
            );  
        }  

    } catch (SQLException e) {  

        response.getWriter().println(  
            "<div class='empty'>" +  
            "<div class='empty-icon'>⚠️</div>" +  
            "<h2>Unable to Load Orders</h2>" +  
            "<p>Please try again later.</p>" +  
            "</div>"  
        );  

        e.printStackTrace();  
    }  

    response.getWriter().println("</div>");  

    /* BOTTOM BUTTONS */  

    response.getWriter().println(  
        "<div class='buttons'>" +  

        "<a class='btn shop' " +  
        "href='http://127.0.0.1:5500/frontend/products.html'>" +  
        "Continue Shopping" +  
        "</a>" +  

        "<a class='btn cart' href='cart'>" +  
        "View Cart" +  
        "</a>" +  

        "</div>"  
    );  

    response.getWriter().println("</div>");  

    response.getWriter().println("</body>");  
    response.getWriter().println("</html>");  
}

}
