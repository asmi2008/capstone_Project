import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // SHOW LOGIN PAGE
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Login</title>");

        response.getWriter().println(
            "<style>" +
            "body { font-family: Arial; margin: 40px; }" +
            "input, button { padding: 10px; margin: 5px; }" +
            "</style>"
        );

        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        response.getWriter().println("<h1>Login</h1>");

        response.getWriter().println(
            "<form method='post' action='login'>"
        );

        response.getWriter().println(
            "<input type='email' name='email' " +
            "placeholder='Email' required>"
        );

        response.getWriter().println("<br>");

        response.getWriter().println(
            "<input type='password' name='password' " +
            "placeholder='Password' required>"
        );

        response.getWriter().println("<br>");

        response.getWriter().println(
            "<button type='submit'>Login</button>"
        );

        response.getWriter().println("</form>");

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }


    // PROCESS LOGIN
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();


        // =====================================
        // ADMIN LOGIN
        // =====================================

        String adminEmail = "admin@fashionstore.com";
        String adminPassword = "Admin@123";

        if (adminEmail.equals(email) &&
            adminPassword.equals(password)) {

            HttpSession session = request.getSession(true);

            session.setAttribute("email", email);
            session.setAttribute("role", "Admin");

            // ADMIN → ADMIN USERS PAGE
            response.sendRedirect(
                request.getContextPath() + "/admin-users"
            );

            return;
        }


        // =====================================
        // SELLER LOGIN
        // =====================================

        boolean sellerLogin =
                userDAO.checkSellerLogin(email, password);

        if (sellerLogin) {

            HttpSession session = request.getSession(true);

            session.setAttribute("email", email);
            session.setAttribute("role", "Seller");

            int sellerId = userDAO.getUserId(email);

            session.setAttribute("userId", sellerId);

            // SELLER → SELLER PRODUCT PAGE
            response.sendRedirect(
                request.getContextPath() + "/seller-product"
            );

            return;
        }


        // =====================================
        // BUYER LOGIN
        // =====================================

        boolean loginSuccess =
                userDAO.checkLogin(email, password);

        if (loginSuccess) {

            HttpSession session = request.getSession(true);

            session.setAttribute("email", email);

            int userId = userDAO.getUserId(email);

            session.setAttribute("userId", userId);
            session.setAttribute("role", "Buyer");

            // BUYER → HOME PAGE
            response.sendRedirect(
                request.getContextPath() + "/home.html"
            );

            return;

        } else {

            // INVALID LOGIN
            response.setContentType(
                "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                "<h2>Invalid Email or Password!</h2>"
            );

            response.getWriter().println(
                "<p><a href='login'>Try Again</a></p>"
            );
        }
    }
}