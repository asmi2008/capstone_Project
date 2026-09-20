import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {

    // SHOW ADMIN LOGIN PAGE
    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Admin Login</title>");

        response.getWriter().println(
            "<style>" +
            "body { font-family: Arial; margin: 40px; }" +
            "input, button { padding: 10px; margin: 5px; }" +
            "</style>"
        );

        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        response.getWriter().println("<h1>Admin Login</h1>");

        response.getWriter().println(
            "<form method='post' action='admin-login'>"
        );

        response.getWriter().println(
            "<input type='email' name='email' " +
            "placeholder='Admin Email' required>"
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


    // PROCESS ADMIN LOGIN
    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // ADMIN LOGIN DETAILS
        String adminEmail = "admin@fashionstore.com";
        String adminPassword = "Admin@123";

        // CHECK ADMIN LOGIN
        if (adminEmail.equals(email) &&
            adminPassword.equals(password)) {

            HttpSession session = request.getSession(true);

            // SET ADMIN ROLE
            session.setAttribute("email", email);
            session.setAttribute("role", "Admin");

            // GO TO ADMIN PAGE
            String url =
                request.getContextPath() + "/admin-users";

            response.sendRedirect(
                response.encodeRedirectURL(url)
            );

        } else {

            // INVALID LOGIN
            response.setContentType(
                "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                "<h2>Invalid Admin Email or Password!</h2>"
            );

            response.getWriter().println(
                "<p><a href='admin-login'>Try Again</a></p>"
            );
        }
    }
}