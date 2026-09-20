import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        HttpSession session = request.getSession(false);

        // Check Buyer Login
        if (session == null ||
            !"Buyer".equals(session.getAttribute("role"))) {

            response.getWriter().println(
                "<h2>Buyer Login Required!</h2>"
            );

            return;
        }

        int userId = (int) session.getAttribute("userId");

        int productId = Integer.parseInt(
            request.getParameter("productId")
        );

        int rating = Integer.parseInt(
            request.getParameter("rating")
        );

        String comment = request.getParameter("comment");

        // Validate rating
        if (rating < 1 || rating > 5) {

            response.getWriter().println(
                "<h2>Rating must be between 1 and 5!</h2>"
            );

            return;
        }

        Review review = new Review(
            0,
            productId,
            userId,
            rating,
            comment
        );

        ReviewDAO reviewDAO = new ReviewDAO();

        reviewDAO.addReview(review);

        response.getWriter().println(
            "<h2>Review Added Successfully!</h2>"
        );
    }
}