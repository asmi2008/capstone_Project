import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chatbot")
public class ChatbotServlet extends HttpServlet {

    // OPEN CHATBOT PAGE
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        response.getWriter().println(
            "<h2>FashionStore AI Chatbot</h2>" +
            "<form method='post' action='chatbot'>" +
            "<input type='text' name='message' " +
            "placeholder='Ask something...' required>" +
            "<button type='submit'>Send</button>" +
            "</form>"
        );
    }

    // CHATBOT RESPONSE
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String message = request.getParameter("message");

        String reply;

        if (message == null || message.trim().isEmpty()) {

            reply = "Please type a message.";

        } else {

            String msg = message.toLowerCase();

            if (msg.contains("hello") || msg.contains("hi")) {

                reply = "Hello! Welcome to FashionStore. How can I help you?";

            } else if (msg.contains("product")) {

                reply = "You can view and search our products from the Products page.";

            } else if (msg.contains("cart")) {

                reply = "You can add products to your cart and update the quantity.";

            } else if (msg.contains("order")) {

                reply = "You can check your previous orders from Order History.";

            } else if (msg.contains("review")) {

                reply = "You can give a 1 to 5 star rating and write a review for products.";

            } else if (msg.contains("seller")) {

                reply = "Sellers can add, edit and delete their own products.";

            } else {

                reply = "Sorry, I didn't understand. You can ask about products, cart, orders, reviews or seller features.";
            }
        }

        response.getWriter().println(
            "<h2>FashionStore AI Chatbot</h2>" +
            "<p><b>You:</b> " + message + "</p>" +
            "<p><b>Bot:</b> " + reply + "</p>" +
            "<br>" +
            "<a href='chatbot'>Ask another question</a>"
        );
    }
}