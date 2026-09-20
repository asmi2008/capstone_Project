import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/seller-login")
public class SellerLoginServlet extends HttpServlet {

    // SHOW SELLER LOGIN PAGE
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Seller Login</title>");

        response.getWriter().println(
            "<style>" +
            "body { font-family: Arial; margin: 40px; }" +
            "input, button { padding: 10px; margin: 5px; }" +
            "</style>"
        );

        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        response.getWriter().println("<h1>Seller Login</h1>");

        response.getWriter().println(
            "<form method='post' action='seller-login'>"
        );

        response.getWriter().println(
            "<input type='email' name='email' " +
            "placeholder='Seller Email' required>"
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


    // PROCESS SELLER LOGIN
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();

        boolean loginSuccess =
                userDAO.checkSellerLogin(email, password);

        if (loginSuccess) {

            HttpSession session =
                    request.getSession(true);

            session.setAttribute("email", email);
            session.setAttribute("role", "Seller");

            int sellerId = userDAO.getUserId(email);

            session.setAttribute("userId", sellerId);

            // SELLER → SELLER PRODUCT PAGE
            response.sendRedirect(
                request.getContextPath() + "/seller-product"
            );

            return;

        } else {

            response.setContentType(
                "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                "<h2>Invalid Seller Email or Password!</h2>"
            );

            response.getWriter().println(
                "<p><a href='seller-login'>Try Again</a></p>"
            );
        }
    }
}