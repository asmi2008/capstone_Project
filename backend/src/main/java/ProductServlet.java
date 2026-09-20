import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        ProductDAO productDAO = new ProductDAO();
        ReviewDAO reviewDAO = new ReviewDAO();

        String keyword = request.getParameter("keyword");
        String category = request.getParameter("category");

        List<Product> products;

        if (keyword != null && !keyword.trim().isEmpty()) {
            products = productDAO.searchProducts(keyword.trim());
        } else if (category != null && !category.trim().isEmpty()) {
            products = productDAO.filterByCategory(category.trim());
        } else {
            products = productDAO.getAllProducts();
        }

        response.getWriter().println("<!DOCTYPE html>");
        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<meta charset='UTF-8'>");
        response.getWriter().println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        response.getWriter().println("<title>AsmiMart - Shop</title>");
        response.getWriter().println("<link rel='stylesheet' href='ui/theme.css'>");

        response.getWriter().println(
            "<style>" +
            "body{margin:0;font-family:Arial,sans-serif;background:#f7f7fb;color:#202044;}" +
            ".shop-header{background:linear-gradient(90deg,#1769e8,#d946ef,#ff6b00);color:white;padding:22px 7%;display:flex;justify-content:space-between;align-items:center;}" +
            ".shop-header h1{margin:0;font-size:28px;}" +
            ".shop-nav a{color:white;text-decoration:none;margin-left:22px;font-weight:bold;}" +
            ".hero{padding:45px 7% 25px;text-align:center;background:white;}" +
            ".hero h2{font-size:42px;margin:10px 0;color:#18235c;}" +
            ".hero p{color:#777;font-size:16px;}" +
            ".filters{display:flex;justify-content:center;gap:10px;flex-wrap:wrap;padding:25px 7%;background:white;}" +
            ".filters input,.filters select{padding:14px 18px;border:1px solid #ddd;border-radius:12px;font-size:15px;min-width:210px;}" +
            ".filters button{padding:14px 25px;border:0;border-radius:12px;background:#1769e8;color:white;font-weight:bold;cursor:pointer;}" +
            ".products{padding:35px 7%;display:grid;grid-template-columns:repeat(auto-fit,minmax(250px,1fr));gap:25px;}" +
            ".product-card{background:white;border-radius:20px;overflow:hidden;box-shadow:0 8px 25px rgba(30,30,80,.10);transition:.3s;}" +
            ".product-card:hover{transform:translateY(-6px);box-shadow:0 14px 35px rgba(30,30,80,.16);}" +
            ".pic{height:270px;background:#f1f3f8;display:flex;align-items:center;justify-content:center;overflow:hidden;}" +
            ".pic img{width:100%;height:100%;object-fit:cover;}" +
            ".product-info{padding:22px;}" +
            ".category{display:inline-block;background:#eee8ff;color:#7048d8;padding:6px 12px;border-radius:20px;font-size:12px;font-weight:bold;}" +
            ".product-info h3{font-size:21px;margin:15px 0 8px;color:#202044;}" +
            ".description{color:#777;min-height:38px;}" +
            ".price{font-size:25px;font-weight:bold;color:#d93687;margin:15px 0;}" +
            ".stock{color:#4d8b57;font-size:14px;margin-bottom:12px;}" +
            ".cart-form{display:flex;gap:8px;margin-bottom:18px;}" +
            ".cart-form input{width:55px;padding:11px;border:1px solid #ddd;border-radius:10px;}" +
            ".cart-form button,.review-form button{border:0;border-radius:10px;padding:11px 16px;background:#ff8a00;color:white;font-weight:bold;cursor:pointer;flex:1;}" +
            ".reviews{border-top:1px solid #eee;padding-top:15px;margin-top:10px;}" +
            ".review-box{background:#faf8ff;border-radius:10px;padding:10px;margin:8px 0;font-size:13px;}" +
            ".stars{color:#f5a623;font-weight:bold;}" +
            ".review-form{margin-top:12px;}" +
            ".review-form select,.review-form input{box-sizing:border-box;width:100%;padding:11px;margin:5px 0;border:1px solid #ddd;border-radius:10px;}" +
            ".review-form button{background:#1769e8;width:100%;}" +
            ".bottom-link{text-align:center;padding:25px;}" +
            ".bottom-link a{color:#1769e8;font-weight:bold;text-decoration:none;}" +
            "@media(max-width:600px){.shop-header{padding:18px;}.shop-nav a{margin-left:8px;font-size:13px;}.hero h2{font-size:30px;}.products{padding:25px 5%;}.pic{height:230px;}}" +
            "</style>"
        );

        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        response.getWriter().println(
            "<header class='shop-header'>" +
            "<h1>AsmiMart</h1>" +
            "<nav class='shop-nav'>" +
            "<a href='home.html'>Home</a>" +
            "<a href='products'>Shop</a>" +
            "<a href='cart'>Cart</a>" +
            "<a href='order-history'>Orders</a>" +
            "</nav>" +
            "</header>"
        );

        response.getWriter().println(
            "<section class='hero'>" +
            "<h2>Discover Your Style</h2>" +
            "<p>Explore our latest fashion collection and find your perfect look.</p>" +
            "</section>"
        );

        response.getWriter().println("<section class='filters'>");

        response.getWriter().println(
            "<form method='get' action='products'>" +
            "<input type='text' name='keyword' placeholder='🔍 Search products...'>" +
            "<button type='submit'>Search</button>" +
            "</form>"
        );

        response.getWriter().println(
            "<form method='get' action='products'>" +
            "<select name='category'>" +
            "<option value=''>All Categories</option>" +
            "<option value='Men'>Men</option>" +
            "<option value='Women'>Women</option>" +
            "<option value='Kids'>Kids</option>" +
            "<option value='Shoes'>Shoes</option>" +
            "<option value='Accessories'>Accessories</option>" +
            "</select>" +
            "<button type='submit'>Filter</button>" +
            "</form>"
        );

        response.getWriter().println("</section>");

        if (products == null || products.isEmpty()) {

            response.getWriter().println(
                "<div style='text-align:center;padding:60px'>" +
                "<h2>No products found!</h2>" +
                "<p>Try another search or category.</p>" +
                "</div>"
            );

        } else {

            response.getWriter().println("<section class='products'>");

            for (Product product : products) {

                String image = "images/tshirt.png";

                if ("Classic Shirt".equalsIgnoreCase(product.name)) {
                    image = "images/shirt.png";
                } else if ("Denim Jeans".equalsIgnoreCase(product.name)) {
                    image = "images/jeans.png";
                } else if ("Women Dress".equalsIgnoreCase(product.name)) {
                    image = "images/womendress.png";
                } else if ("Women Top".equalsIgnoreCase(product.name)) {
                    image = "images/womentop.png";
                } else if ("Kids T-Shirt".equalsIgnoreCase(product.name)) {
                    image = "images/kidstshirt.png";
                } else if ("Kids Dress".equalsIgnoreCase(product.name)) {
                    image = "images/kidsdress.png";
                } else if ("Running Shoes".equalsIgnoreCase(product.name)) {
                    image = "images/shoes.png";
                } else if ("Fashion Handbag".equalsIgnoreCase(product.name)) {
                    image = "images/handbag.png";
                } else if ("Leather Belt".equalsIgnoreCase(product.name)) {
                    image = "images/belt.png";
                }

                response.getWriter().println("<div class='product-card'>");

                response.getWriter().println(
                    "<div class='pic'>" +
                    "<img src='" + image + "' alt='" + product.name + "'>" +
                    "</div>"
                );

                response.getWriter().println("<div class='product-info'>");

                response.getWriter().println(
                    "<span class='category'>" + product.category + "</span>"
                );

                response.getWriter().println(
                    "<h3>" + product.name + "</h3>"
                );

                response.getWriter().println(
                    "<p class='description'>" + product.description + "</p>"
                );

                response.getWriter().println(
                    "<div class='price'>₹" +
                    String.format("%.2f", product.price) +
                    "</div>"
                );

                response.getWriter().println(
                    "<div class='stock'>✓ " +
                    product.stock +
                    " items available</div>"
                );

                response.getWriter().println(
                    "<form class='cart-form' method='post' action='cart'>" +
                    "<input type='hidden' name='action' value='add'>" +
                    "<input type='hidden' name='productId' value='" + product.id + "'>" +
                    "<input type='number' name='quantity' value='1' min='1' max='" + product.stock + "'>" +
                    "<button type='submit'>🛒 Add to Cart</button>" +
                    "</form>"
                );

                response.getWriter().println("<div class='reviews'>");

                List<Review> reviews =
                    reviewDAO.getReviewsByProduct(product.id);

                if (reviews.isEmpty()) {

                    response.getWriter().println(
                        "<p style='color:#888'>No reviews yet.</p>"
                    );

                } else {

                    for (Review review : reviews) {

                        response.getWriter().println(
                            "<div class='review-box'>" +
                            "<div class='stars'>★★★★★ " +
                            review.rating + "/5</div>" +
                            "<div>" + review.comment + "</div>" +
                            "</div>"
                        );
                    }
                }

                response.getWriter().println(
                    "<form class='review-form' method='post' action='review'>" +
                    "<input type='hidden' name='productId' value='" + product.id + "'>" +
                    "<select name='rating' required>" +
                    "<option value=''>Give Rating</option>" +
                    "<option value='5'>★★★★★ 5 Stars</option>" +
                    "<option value='4'>★★★★ 4 Stars</option>" +
                    "<option value='3'>★★★ 3 Stars</option>" +
                    "<option value='2'>★★ 2 Stars</option>" +
                    "<option value='1'>★ 1 Star</option>" +
                    "</select>" +
                    "<input type='text' name='comment' placeholder='Write your review...' required>" +
                    "<button type='submit'>Submit Review</button>" +
                    "</form>"
                );

                response.getWriter().println("</div>");
                response.getWriter().println("</div>");
                response.getWriter().println("</div>");
            }

            response.getWriter().println("</section>");
        }

        response.getWriter().println(
            "<div class='bottom-link'>" +
            "<a href='cart'>🛒 View Cart</a>" +
            "</div>"
        );

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}