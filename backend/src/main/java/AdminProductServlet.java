import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin-products")
public class AdminProductServlet extends HttpServlet {

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
                "background:#f4f6fb;" +
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

        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();

        response.getWriter().println(

            "<!DOCTYPE html>" +
            "<html>" +

            "<head>" +

            "<meta charset='UTF-8'>" +

            "<meta name='viewport' " +
            "content='width=device-width, initial-scale=1.0'>" +

            "<title>Admin Products - Asmi Mart</title>" +

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
            "width:94%;" +
            "max-width:1400px;" +
            "margin:35px auto;" +
            "}" +

            ".page-title{" +
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

            /* Product card */

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
            "min-width:1000px;" +
            "}" +

            "th{" +
            "background:#667eea;" +
            "color:white;" +
            "padding:14px 12px;" +
            "text-align:left;" +
            "font-size:13px;" +
            "white-space:nowrap;" +
            "}" +

            "th:first-child{" +
            "border-radius:8px 0 0 8px;" +
            "}" +

            "th:last-child{" +
            "border-radius:0 8px 8px 0;" +
            "}" +

            "td{" +
            "padding:14px 12px;" +
            "border-bottom:1px solid #eee;" +
            "font-size:14px;" +
            "vertical-align:middle;" +
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

            ".price{" +
            "font-weight:bold;" +
            "color:#2e7d32;" +
            "}" +

            ".stock{" +
            "background:#fff4df;" +
            "color:#b26a00;" +
            "padding:6px 10px;" +
            "border-radius:7px;" +
            "font-weight:bold;" +
            "}" +

            ".category{" +
            "background:#f0eaff;" +
            "color:#764ba2;" +
            "padding:6px 10px;" +
            "border-radius:15px;" +
            "font-size:12px;" +
            "font-weight:bold;" +
            "}" +

            ".seller{" +
            "font-weight:bold;" +
            "color:#555;" +
            "}" +

            /* Remove button */

            ".remove-button{" +
            "background:#e53935;" +
            "color:white;" +
            "border:none;" +
            "padding:9px 16px;" +
            "border-radius:7px;" +
            "font-size:13px;" +
            "font-weight:bold;" +
            "cursor:pointer;" +
            "}" +

            ".remove-button:hover{" +
            "background:#c62828;" +
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

            ".users{" +
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

            "<h1>📦 Products Management</h1>" +

            "<p>View and manage all products in your fashion store</p>" +

            "</div>" +

            "<div class='table-card'>" +

            "<div class='table-title'>All Products</div>" +

            "<table>" +

            "<tr>" +
            "<th>ID</th>" +
            "<th>Name</th>" +
            "<th>Description</th>" +
            "<th>Price</th>" +
            "<th>Category</th>" +
            "<th>Stock</th>" +
            "<th>Seller ID</th>" +
            "<th>Action</th>" +
            "</tr>"
        );

        for (Product product : products) {

            response.getWriter().println(

                "<tr>" +

                "<td>" +
                "<span class='id-badge'>" +
                product.id +
                "</span>" +
                "</td>" +

                "<td>" +
                "<strong>" +
                product.name +
                "</strong>" +
                "</td>" +

                "<td>" +
                product.description +
                "</td>" +

                "<td>" +
                "<span class='price'>" +
                "₹" + product.price +
                "</span>" +
                "</td>" +

                "<td>" +
                "<span class='category'>" +
                product.category +
                "</span>" +
                "</td>" +

                "<td>" +
                "<span class='stock'>" +
                product.stock +
                "</span>" +
                "</td>" +

                "<td>" +
                "<span class='seller'>" +
                product.sellerId +
                "</span>" +
                "</td>" +

                "<td>" +

                "<form method='post' action='admin-products'>" +

                "<input type='hidden' " +
                "name='productId' " +
                "value='" + product.id + "'>" +

                "<button type='submit' " +
                "class='remove-button'>" +
                "Remove" +
                "</button>" +

                "</form>" +

                "</td>" +

                "</tr>"
            );
        }

        response.getWriter().println(

            "</table>" +

            "</div>" +

            /* Navigation */

            "<div class='navigation'>" +

            "<a href='admin-users' " +
            "class='nav-button users'>" +
            "👥 View All Users" +
            "</a>" +

            "<a href='admin-orders' " +
            "class='nav-button orders'>" +
            "🛒 View All Orders" +
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


    protected void doPost(HttpServletRequest request,
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
                "background:#f4f6fb;" +
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

        int productId = Integer.parseInt(
            request.getParameter("productId")
        );

        ProductDAO productDAO = new ProductDAO();

        productDAO.deleteProductByAdmin(productId);

        response.getWriter().println(

            "<html>" +

            "<head>" +

            "<title>Product Removed - Asmi Mart</title>" +

            "<style>" +

            "body{" +
            "font-family:Arial,sans-serif;" +
            "background:#f4f6fb;" +
            "display:flex;" +
            "justify-content:center;" +
            "align-items:center;" +
            "height:100vh;" +
            "}" +

            ".success-card{" +
            "background:white;" +
            "padding:45px;" +
            "border-radius:18px;" +
            "box-shadow:0 8px 30px rgba(0,0,0,0.12);" +
            "text-align:center;" +
            "width:400px;" +
            "}" +

            ".icon{" +
            "font-size:50px;" +
            "margin-bottom:15px;" +
            "}" +

            "h2{" +
            "color:#2e7d32;" +
            "margin-bottom:10px;" +
            "}" +

            "p{" +
            "color:#777;" +
            "margin-bottom:25px;" +
            "}" +

            "a{" +
            "display:inline-block;" +
            "background:#667eea;" +
            "color:white;" +
            "text-decoration:none;" +
            "padding:12px 22px;" +
            "border-radius:8px;" +
            "font-weight:bold;" +
            "}" +

            "a:hover{" +
            "background:#5568d8;" +
            "}" +

            "</style>" +

            "</head>" +

            "<body>" +

            "<div class='success-card'>" +

            "<div class='icon'>✅</div>" +

            "<h2>Product Removed Successfully!</h2>" +

            "<p>The product has been removed from the store.</p>" +

            "<a href='admin-products'>" +
            "← Back to Admin Products" +
            "</a>" +

            "</div>" +

            "</body>" +

            "</html>"
        );
    }
}