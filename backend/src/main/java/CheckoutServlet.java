import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        System.out.println("CHECKOUT SESSION: " + session);
        System.out.println(
            "CHECKOUT USER ID: " +
            (session == null ? null : session.getAttribute("userId"))
        );
        if (session == null || session.getAttribute("userId") == null) {
            response.getWriter().println(
                "<h2>Please login first!</h2>"
            );
            return;
        }

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
            "<title>Checkout - AsmiMart</title>"
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

        response.getWriter().println(
            ".container {" +
            "max-width: 650px;" +
            "margin: 70px auto;" +
            "padding: 20px;" +
            "}"
        );

        response.getWriter().println(
            ".checkout-card {" +
            "background: white;" +
            "border-radius: 24px;" +
            "padding: 45px;" +
            "text-align: center;" +
            "box-shadow: 0 12px 35px rgba(80,30,100,0.15);" +
            "}"
        );

        response.getWriter().println(
            ".icon {" +
            "width: 85px;" +
            "height: 85px;" +
            "margin: 0 auto 25px;" +
            "border-radius: 50%;" +
            "background: linear-gradient(135deg, #8e24aa, #d81b60);" +
            "color: white;" +
            "font-size: 42px;" +
            "display: flex;" +
            "align-items: center;" +
            "justify-content: center;" +
            "}"
        );

        response.getWriter().println(
            "h1 {" +
            "font-size: 32px;" +
            "margin-bottom: 15px;" +
            "color: #4a126b;" +
            "}"
        );

        response.getWriter().println(
            ".message {" +
            "font-size: 17px;" +
            "color: #666;" +
            "line-height: 1.6;" +
            "margin-bottom: 30px;" +
            "}"
        );

        response.getWriter().println(
            ".secure {" +
            "background: #fff4fa;" +
            "border-radius: 12px;" +
            "padding: 14px;" +
            "margin-bottom: 30px;" +
            "color: #7a3158;" +
            "font-size: 14px;" +
            "}"
        );

        response.getWriter().println(
            ".buttons {" +
            "display: flex;" +
            "justify-content: center;" +
            "gap: 15px;" +
            "flex-wrap: wrap;" +
            "}"
        );

        response.getWriter().println(
            ".btn {" +
            "display: inline-block;" +
            "padding: 13px 28px;" +
            "border-radius: 30px;" +
            "border: none;" +
            "font-size: 16px;" +
            "font-weight: bold;" +
            "cursor: pointer;" +
            "text-decoration: none;" +
            "}"
        );

        response.getWriter().println(
            ".confirm {" +
            "background: linear-gradient(135deg, #8e24aa, #d81b60);" +
            "color: white;" +
            "box-shadow: 0 6px 15px rgba(142,36,170,0.25);" +
            "}"
        );

        response.getWriter().println(
            ".back {" +
            "background: #f0eaf5;" +
            "color: #5a3d68;" +
            "}"
        );

        response.getWriter().println(
            ".footer {" +
            "text-align: center;" +
            "margin-top: 35px;" +
            "color: #888;" +
            "font-size: 13px;" +
            "}"
        );

        response.getWriter().println(
            "@media (max-width: 600px) {" +
            ".navbar { padding: 16px 5%; }" +
            ".nav-links { gap: 10px; font-size: 13px; }" +
            ".container { margin: 40px auto; }" +
            ".checkout-card { padding: 30px 20px; }" +
            "h1 { font-size: 26px; }" +
            "}"
        );

        response.getWriter().println("</style>");
        response.getWriter().println("</head>");

        response.getWriter().println("<body>");

        // NAVBAR
        response.getWriter().println(
            "<div class='navbar'>" +
            "<div class='logo'>AsmiMart</div>" +
            "<div class='nav-links'>" +
            "<a href='http://127.0.0.1:5500/frontend/home.html'>Home</a>" +
            "<a href='http://127.0.0.1:5500/frontend/products.html'>Shop</a>" +
            "</div>" +
            "</div>"
        );

        // CHECKOUT CARD
        response.getWriter().println(
            "<div class='container'>"
        );

        response.getWriter().println(
            "<div class='checkout-card'>"
        );

        response.getWriter().println(
            "<div class='icon'>🛍️</div>"
        );

        response.getWriter().println(
            "<h1>Confirm Your Order</h1>"
        );

        response.getWriter().println(
            "<p class='message'>" +
            "You're just one step away from completing your order. " +
            "Please confirm your order to continue." +
            "</p>"
        );

        response.getWriter().println(
            "<div class='secure'>" +
            "🔒 Your order will be securely saved in your account." +
            "</div>"
        );

        response.getWriter().println(
            "<div class='buttons'>"
        );

        response.getWriter().println(
            "<form method='post' action='checkout' " +
            "style='display:inline;'>"
        );

        response.getWriter().println(
            "<button type='submit' class='btn confirm'>" +
            "✓ Confirm Order" +
            "</button>"
        );

        response.getWriter().println("</form>");

        response.getWriter().println(
            "<a class='btn back' href='cart'>" +
            "← Back to Cart" +
            "</a>"
        );

        response.getWriter().println("</div>");

        response.getWriter().println("</div>");

        response.getWriter().println(
            "<div class='footer'>" +
            "Thank you for shopping with AsmiMart ❤️" +
            "</div>"
        );

        response.getWriter().println("</div>");

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        System.out.println("CHECKOUT POST SESSION: " + session);
        System.out.println(
            "CHECKOUT POST USER ID: " +
            (session == null ? null : session.getAttribute("userId"))
        );

        if (session == null || session.getAttribute("userId") == null) {
            response.getWriter().println(
                "<h2>Please login first!</h2>"
            );
            return;
        }

        int userId = (int) session.getAttribute("userId");

        CheckoutDAO checkoutDAO = new CheckoutDAO();

        checkoutDAO.checkout(userId);

        response.setContentType(
            "text/html;charset=UTF-8"
        );

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
            "<title>Order Confirmed - AsmiMart</title>"
        );

        response.getWriter().println("<style>");

        response.getWriter().println(
            "* { box-sizing: border-box; margin: 0; padding: 0; }"
        );

        response.getWriter().println(
            "body {" +
            "font-family: Arial, sans-serif;" +
            "background: linear-gradient(135deg, #f5e8ff, #fff3f8);" +
            "min-height: 100vh;" +
            "display: flex;" +
            "align-items: center;" +
            "justify-content: center;" +
            "padding: 20px;" +
            "}"
        );

        response.getWriter().println(
            ".success-card {" +
            "background: white;" +
            "max-width: 600px;" +
            "width: 100%;" +
            "padding: 50px 35px;" +
            "border-radius: 25px;" +
            "text-align: center;" +
            "box-shadow: 0 15px 40px rgba(80,30,100,0.16);" +
            "}"
        );

        response.getWriter().println(
            ".success-icon {" +
            "width: 90px;" +
            "height: 90px;" +
            "margin: 0 auto 25px;" +
            "border-radius: 50%;" +
            "background: linear-gradient(135deg, #8e24aa, #d81b60);" +
            "color: white;" +
            "font-size: 48px;" +
            "display: flex;" +
            "align-items: center;" +
            "justify-content: center;" +
            "}"
        );

        response.getWriter().println(
            "h1 {" +
            "color: #4a126b;" +
            "font-size: 32px;" +
            "margin-bottom: 15px;" +
            "}"
        );

        response.getWriter().println(
            "p {" +
            "color: #666;" +
            "font-size: 17px;" +
            "line-height: 1.6;" +
            "margin-bottom: 30px;" +
            "}"
        );

        response.getWriter().println(
            ".btn {" +
            "display: inline-block;" +
            "padding: 14px 25px;" +
            "margin: 7px;" +
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

        response.getWriter().println("</style>");
        response.getWriter().println("</head>");

        response.getWriter().println("<body>");

        response.getWriter().println(
            "<div class='success-card'>"
        );

        response.getWriter().println(
            "<div class='success-icon'>✓</div>"
        );

        response.getWriter().println(
            "<h1>Order Confirmed!</h1>"
        );

        response.getWriter().println(
            "<p>" +
            "Your order has been placed successfully. " +
            "Thank you for shopping with AsmiMart!" +
            "</p>"
        );

        response.getWriter().println(
            "<a class='btn shop' " +
            "href='http://127.0.0.1:5500/frontend/products.html'>" +
            "Continue Shopping" +
            "</a>"
        );

        response.getWriter().println(
            "<a class='btn cart' href='cart'>" +
            "View Cart" +
            "</a>"
        );

        response.getWriter().println(
            "</div>"
        );

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
}































