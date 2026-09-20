import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Get form values
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        response.setContentType("text/html;charset=UTF-8");

        // Validate role
        if (role == null || role.trim().isEmpty()) {

            showMessage(
                response,
                "Please Select Account Type",
                "Please select Buyer, Seller, or Admin.",
                "Back to Register",
                "http://127.0.0.1:5500/frontend/register.html"
            );

            return;
        }

        try {

            // Create user
            User user = new User(
                0,
                name,
                email,
                password
            );

            // Set selected role
            user.setRole(role);

            // Save user
            UserDAO userDAO = new UserDAO();

            boolean success = userDAO.addUser(user);

            if (success) {

                // Registration Success Page
                response.getWriter().println(
                    "<!DOCTYPE html>"
                );

                response.getWriter().println(
                    "<html lang='en'>"
                );

                response.getWriter().println(
                    "<head>"
                );

                response.getWriter().println(
                    "<meta charset='UTF-8'>"
                );

                response.getWriter().println(
                    "<meta name='viewport' " +
                    "content='width=device-width, initial-scale=1.0'>"
                );

                response.getWriter().println(
                    "<title>Registration Successful - AsmiMart</title>"
                );

                response.getWriter().println(

                    "<style>" +

                    "* {" +
                    "box-sizing: border-box;" +
                    "margin: 0;" +
                    "padding: 0;" +
                    "}" +

                    "body {" +
                    "font-family: Arial, sans-serif;" +
                    "min-height: 100vh;" +
                    "background: linear-gradient(135deg, #f7e9ff, #fff4f8);" +
                    "color: #2d1740;" +
                    "}" +

                    ".navbar {" +
                    "height: 78px;" +
                    "display: flex;" +
                    "align-items: center;" +
                    "justify-content: space-between;" +
                    "padding: 0 7%;" +
                    "background: linear-gradient(135deg, #5b1a91, #d81b60);" +
                    "color: white;" +
                    "box-shadow: 0 5px 20px rgba(80,30,100,0.20);" +
                    "}" +

                    ".brand {" +
                    "font-size: 28px;" +
                    "font-weight: bold;" +
                    "letter-spacing: 0.5px;" +
                    "}" +

                    ".brand span {" +
                    "color: #ff4f9a;" +
                    "}" +

                    ".nav-links {" +
                    "display: flex;" +
                    "gap: 30px;" +
                    "}" +

                    ".nav-links a {" +
                    "color: white;" +
                    "text-decoration: none;" +
                    "font-weight: bold;" +
                    "font-size: 15px;" +
                    "}" +

                    ".page {" +
                    "min-height: calc(100vh - 78px);" +
                    "display: flex;" +
                    "align-items: center;" +
                    "justify-content: center;" +
                    "padding: 50px 20px;" +
                    "}" +

                    ".success-card {" +
                    "width: 100%;" +
                    "max-width: 850px;" +
                    "background: white;" +
                    "border-radius: 28px;" +
                    "padding: 50px;" +
                    "text-align: center;" +
                    "box-shadow: 0 18px 50px rgba(85,30,110,0.16);" +
                    "}" +

                    ".success-icon {" +
                    "width: 90px;" +
                    "height: 90px;" +
                    "margin: 0 auto 25px;" +
                    "border-radius: 50%;" +
                    "display: flex;" +
                    "align-items: center;" +
                    "justify-content: center;" +
                    "background: linear-gradient(135deg, #32c671, #16a85a);" +
                    "color: white;" +
                    "font-size: 48px;" +
                    "font-weight: bold;" +
                    "box-shadow: 0 10px 25px rgba(30,170,90,0.25);" +
                    "}" +

                    ".success-card h1 {" +
                    "font-size: 38px;" +
                    "margin-bottom: 12px;" +
                    "background: linear-gradient(90deg, #5b1a91, #d81b60);" +
                    "-webkit-background-clip: text;" +
                    "-webkit-text-fill-color: transparent;" +
                    "}" +

                    ".welcome {" +
                    "font-size: 20px;" +
                    "color: #5f5270;" +
                    "margin-bottom: 30px;" +
                    "}" +

                    ".welcome strong {" +
                    "color: #d81b60;" +
                    "}" +

                    ".details {" +
                    "max-width: 650px;" +
                    "margin: 0 auto 30px;" +
                    "padding: 25px 30px;" +
                    "border-radius: 18px;" +
                    "background: linear-gradient(135deg, #faf4ff, #fff5fa);" +
                    "text-align: left;" +
                    "}" +

                    ".detail-row {" +
                    "display: grid;" +
                    "grid-template-columns: 180px 20px 1fr;" +
                    "padding: 12px 0;" +
                    "font-size: 16px;" +
                    "}" +

                    ".detail-label {" +
                    "color: #756685;" +
                    "font-weight: 600;" +
                    "}" +

                    ".detail-value {" +
                    "color: #28123b;" +
                    "font-weight: bold;" +
                    "word-break: break-word;" +
                    "}" +

                    ".message {" +
                    "color: #62576c;" +
                    "font-size: 16px;" +
                    "line-height: 1.6;" +
                    "margin-bottom: 30px;" +
                    "}" +

                    ".buttons {" +
                    "display: flex;" +
                    "justify-content: center;" +
                    "gap: 15px;" +
                    "flex-wrap: wrap;" +
                    "}" +

                    ".btn {" +
                    "display: inline-block;" +
                    "padding: 14px 32px;" +
                    "border-radius: 30px;" +
                    "text-decoration: none;" +
                    "font-weight: bold;" +
                    "font-size: 16px;" +
                    "transition: 0.2s;" +
                    "}" +

                    ".login-btn {" +
                    "color: white;" +
                    "background: linear-gradient(135deg, #5b1a91, #d81b60);" +
                    "box-shadow: 0 7px 18px rgba(142,36,170,0.25);" +
                    "}" +

                    ".back-btn {" +
                    "color: #5b1a91;" +
                    "background: #f1e9f7;" +
                    "}" +

                    ".btn:hover {" +
                    "transform: translateY(-2px);" +
                    "}" +

                    ".footer {" +
                    "text-align: center;" +
                    "padding: 20px;" +
                    "color: #82738e;" +
                    "font-size: 14px;" +
                    "}" +

                    "@media (max-width: 600px) {" +

                    ".navbar {" +
                    "padding: 0 20px;" +
                    "}" +

                    ".brand {" +
                    "font-size: 23px;" +
                    "}" +

                    ".nav-links {" +
                    "gap: 12px;" +
                    "}" +

                    ".nav-links a {" +
                    "font-size: 13px;" +
                    "}" +

                    ".success-card {" +
                    "padding: 35px 20px;" +
                    "}" +

                    ".success-card h1 {" +
                    "font-size: 30px;" +
                    "}" +

                    ".welcome {" +
                    "font-size: 17px;" +
                    "}" +

                    ".detail-row {" +
                    "grid-template-columns: 115px 15px 1fr;" +
                    "font-size: 14px;" +
                    "}" +

                    "}" +

                    "</style>"
                );

                response.getWriter().println(
                    "</head>"
                );

                response.getWriter().println(
                    "<body>"
                );

                // Navbar
                response.getWriter().println(
                    "<nav class='navbar'>" +
                    "<div class='brand'>Asmi<span>Mart</span></div>" +
                    "<div class='nav-links'>" +
                    "<a href='http://127.0.0.1:5500/frontend/home.html'>Home</a>" +
                    "<a href='http://127.0.0.1:5500/frontend/products.html'>Shop</a>" +
                    "<a href='http://127.0.0.1:5500/frontend/index.html'>Login</a>" +
                    "</div>" +
                    "</nav>"
                );

                // Main page
                response.getWriter().println(
                    "<main class='page'>"
                );

                response.getWriter().println(
                    "<div class='success-card'>"
                );

                // Success icon
                response.getWriter().println(
                    "<div class='success-icon'>✓</div>"
                );

                response.getWriter().println(
                    "<h1>Registration Successful!</h1>"
                );

                response.getWriter().println(
                    "<p class='welcome'>" +
                    "Welcome to AsmiMart, <strong>" +
                    name +
                    "!</strong></p>"
                );

                // Account details
                response.getWriter().println(
                    "<div class='details'>"
                );

                response.getWriter().println(
                    "<div class='detail-row'>" +
                    "<div class='detail-label'>Your Name</div>" +
                    "<div>:</div>" +
                    "<div class='detail-value'>" +
                    name +
                    "</div>" +
                    "</div>"
                );

                response.getWriter().println(
                    "<div class='detail-row'>" +
                    "<div class='detail-label'>Your Email</div>" +
                    "<div>:</div>" +
                    "<div class='detail-value'>" +
                    email +
                    "</div>" +
                    "</div>"
                );

                response.getWriter().println(
                    "<div class='detail-row'>" +
                    "<div class='detail-label'>Account Type</div>" +
                    "<div>:</div>" +
                    "<div class='detail-value'>" +
                    role +
                    "</div>" +
                    "</div>"
                );

                response.getWriter().println(
                    "</div>"
                );

                response.getWriter().println(
                    "<p class='message'>" +
                    "Your account has been created successfully.<br>" +
                    "You can now log in and start exploring amazing fashion products!" +
                    "</p>"
                );

                // Buttons
                response.getWriter().println(
                    "<div class='buttons'>"
                );

                response.getWriter().println(
                    "<a class='btn login-btn' " +
                    "href='http://127.0.0.1:5500/frontend/index.html'>" +
                    "→ Go to Login" +
                    "</a>"
                );

                response.getWriter().println(
                    "<a class='btn back-btn' " +
                    "href='http://127.0.0.1:5500/frontend/home.html'>" +
                    "← Home" +
                    "</a>"
                );

                response.getWriter().println(
                    "</div>"
                );

                response.getWriter().println(
                    "</div>"
                );

                response.getWriter().println(
                    "</main>"
                );

                // Footer
                response.getWriter().println(
                    "<div class='footer'>" +
                    "© 2026 AsmiMart • Fashion E-Commerce" +
                    "</div>"
                );

                response.getWriter().println(
                    "</body></html>"
                );

            } else {

                // Email already exists
                showMessage(
                    response,
                    "Email Already Exists!",
                    "An account with this email already exists. " +
                    "Please use a different email address.",
                    "Try Again",
                    "http://127.0.0.1:5500/frontend/register.html"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(
                response,
                "Registration Failed!",
                "Something went wrong while creating your account. " +
                "Please try again.",
                "Back to Register",
                "http://127.0.0.1:5500/frontend/register.html"
            );
        }
    }

    // Common professional message page
    private void showMessage(HttpServletResponse response,
                             String title,
                             String message,
                             String buttonText,
                             String buttonLink)
            throws IOException {

        response.getWriter().println(
            "<!DOCTYPE html>" +
            "<html lang='en'>" +
            "<head>" +
            "<meta charset='UTF-8'>" +
            "<meta name='viewport' " +
            "content='width=device-width, initial-scale=1.0'>" +
            "<title>AsmiMart</title>" +

            "<style>" +

            "* {" +
            "box-sizing: border-box;" +
            "}" +

            "body {" +
            "margin: 0;" +
            "font-family: Arial, sans-serif;" +
            "min-height: 100vh;" +
            "display: flex;" +
            "align-items: center;" +
            "justify-content: center;" +
            "background: linear-gradient(135deg, #f7e9ff, #fff4f8);" +
            "padding: 20px;" +
            "}" +

            ".card {" +
            "width: 100%;" +
            "max-width: 600px;" +
            "background: white;" +
            "padding: 50px 35px;" +
            "border-radius: 25px;" +
            "text-align: center;" +
            "box-shadow: 0 15px 40px rgba(80,30,100,0.15);" +
            "}" +

            ".brand {" +
            "font-size: 30px;" +
            "font-weight: bold;" +
            "color: #5b1a91;" +
            "margin-bottom: 30px;" +
            "}" +

            ".brand span {" +
            "color: #d81b60;" +
            "}" +

            ".icon {" +
            "font-size: 55px;" +
            "margin-bottom: 20px;" +
            "}" +

            "h1 {" +
            "color: #4a126b;" +
            "margin-bottom: 15px;" +
            "}" +

            "p {" +
            "color: #666;" +
            "font-size: 17px;" +
            "line-height: 1.6;" +
            "margin-bottom: 30px;" +
            "}" +

            "a {" +
            "display: inline-block;" +
            "padding: 14px 30px;" +
            "border-radius: 30px;" +
            "text-decoration: none;" +
            "font-weight: bold;" +
            "color: white;" +
            "background: linear-gradient(135deg, #5b1a91, #d81b60);" +
            "}" +

            "</style>" +
            "</head>" +

            "<body>" +

            "<div class='card'>" +

            "<div class='brand'>Asmi<span>Mart</span></div>" +

            "<div class='icon'>⚠</div>" +

            "<h1>" +
            title +
            "</h1>" +

            "<p>" +
            message +
            "</p>" +

            "<a href='" +
            buttonLink +
            "'>" +
            buttonText +
            "</a>" +

            "</div>" +

            "</body>" +
            "</html>"
        );
    }
}