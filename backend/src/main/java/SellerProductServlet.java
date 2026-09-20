import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/seller-product")
public class SellerProductServlet extends HttpServlet {

    // =========================
    // SHOW SELLER PRODUCT PAGE
    // =========================
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);

        // Check Seller Login
        if (session == null ||
            !"Seller".equals(session.getAttribute("role"))) {

            response.getWriter().println(
                "<h2>Seller Login Required!</h2>"
            );

            return;
        }

        int sellerId = 11;

        ProductDAO productDAO = new ProductDAO();

        List<Product> products = productDAO.getAllProducts();

        response.getWriter().println("<html>");
        response.getWriter().println("<head>");
        response.getWriter().println("<title>Manage Products</title>");

        // =========================
        // DESIGN / COLOUR ONLY
        // =========================

        response.getWriter().println(
            "<style>" +

            "body {" +
            "font-family: Arial, sans-serif;" +
            "background-color: #f5f0ff;" +
            "color: #333;" +
            "margin: 0;" +
            "padding: 40px;" +
            "}" +

            "h1 {" +
            "text-align: center;" +
            "color: #6a1b9a;" +
            "margin-bottom: 35px;" +
            "}" +

            "h2 {" +
            "color: #7b1fa2;" +
            "margin-top: 30px;" +
            "}" +

            "input, select {" +
            "padding: 10px;" +
            "margin: 5px;" +
            "border: 1px solid #d1b3e8;" +
            "border-radius: 7px;" +
            "background-color: white;" +
            "}" +

            "input:focus, select:focus {" +
            "outline: none;" +
            "border-color: #8e24aa;" +
            "}" +

            "button {" +
            "padding: 10px 18px;" +
            "margin: 5px;" +
            "border: none;" +
            "border-radius: 7px;" +
            "background-color: #8e24aa;" +
            "color: white;" +
            "font-weight: bold;" +
            "cursor: pointer;" +
            "}" +

            "button:hover {" +
            "background-color: #6a1b9a;" +
            "}" +

            "table {" +
            "width: 100%;" +
            "border-collapse: collapse;" +
            "margin-top: 20px;" +
            "background-color: white;" +
            "box-shadow: 0 3px 10px rgba(0,0,0,0.10);" +
            "}" +

            "th {" +
            "background-color: #8e24aa;" +
            "color: white;" +
            "padding: 13px;" +
            "}" +

            "td {" +
            "border: 1px solid #e0d5e8;" +
            "padding: 10px;" +
            "}" +

            "tr:nth-child(even) {" +
            "background-color: #faf7fc;" +
            "}" +

            "tr:hover {" +
            "background-color: #f0e5f7;" +
            "}" +

            "a {" +
            "color: #7b1fa2;" +
            "font-weight: bold;" +
            "text-decoration: none;" +
            "}" +

            "a:hover {" +
            "text-decoration: underline;" +
            "}" +

            "</style>"
        );

        response.getWriter().println("</head>");
        response.getWriter().println("<body>");

        response.getWriter().println(
            "<h1>Seller - Manage Products</h1>"
        );

        // =========================
        // ADD PRODUCT FORM
        // =========================

        response.getWriter().println("<h2>Add New Product</h2>");

        response.getWriter().println(
            "<form method='post' action='seller-product'>"
        );

        response.getWriter().println(
            "<input type='hidden' name='action' value='add'>"
        );

        response.getWriter().println(
            "<input type='text' name='name' " +
            "placeholder='Product Name' required><br>"
        );

        response.getWriter().println(
            "<input type='text' name='description' " +
            "placeholder='Description' required><br>"
        );

        response.getWriter().println(
            "<input type='number' step='0.01' name='price' " +
            "placeholder='Price' required><br>"
        );

        response.getWriter().println(
            "<select name='category' required>" +
            "<option value=''>Select Category</option>" +
            "<option value='Men'>Men</option>" +
            "<option value='Women'>Women</option>" +
            "<option value='Kids'>Kids</option>" +
            "</select><br>"
        );

        response.getWriter().println(
            "<input type='number' name='stock' " +
            "placeholder='Stock' min='0' required><br>"
        );

        response.getWriter().println(
            "<button type='submit'>Add Product</button>"
        );

        response.getWriter().println("</form>");

        // =========================
        // SHOW PRODUCTS
        // =========================

        response.getWriter().println("<h2>My Products</h2>");

        response.getWriter().println("<table>");

        response.getWriter().println(
            "<tr>" +
            "<th>ID</th>" +
            "<th>Name</th>" +
            "<th>Description</th>" +
            "<th>Price</th>" +
            "<th>Category</th>" +
            "<th>Stock</th>" +
            "<th>Action</th>" +
            "</tr>"
        );

        boolean found = false;

        for (Product product : products) {

            if (product.sellerId == sellerId) {

                found = true;

                response.getWriter().println(
                    "<tr>" +
                    "<td>" + product.id + "</td>" +
                    "<td>" + product.name + "</td>" +
                    "<td>" + product.description + "</td>" +
                    "<td>₹" + product.price + "</td>" +
                    "<td>" + product.category + "</td>" +
                    "<td>" + product.stock + "</td>" +

                    "<td>" +

                    // EDIT
                    "<form method='post' action='seller-product' style='display:inline;'>" +

                    "<input type='hidden' name='action' value='edit'>" +
                    "<input type='hidden' name='id' value='" + product.id + "'>" +

                    "<input type='text' name='name' value='" + product.name + "' required>" +
                    "<input type='text' name='description' value='" + product.description + "' required>" +
                    "<input type='number' step='0.01' name='price' value='" + product.price + "' required>" +

                    "<select name='category'>" +
                    "<option value='Men'>Men</option>" +
                    "<option value='Women'>Women</option>" +
                    "<option value='Kids'>Kids</option>" +
                    "</select>" +

                    "<input type='number' name='stock' value='" + product.stock + "' min='0' required>" +

                    "<button type='submit'>Edit</button>" +

                    "</form>" +

                    // DELETE
                    "<form method='post' action='seller-product' style='display:inline;'>" +

                    "<input type='hidden' name='action' value='delete'>" +
                    "<input type='hidden' name='id' value='" + product.id + "'>" +

                    "<button type='submit'>Delete</button>" +

                    "</form>" +

                    "</td>" +
                    "</tr>"
                );
            }
        }

        response.getWriter().println("</table>");

        if (!found) {
            response.getWriter().println(
                "<h3>No products found for this seller.</h3>"
            );
        }

        response.getWriter().println(
            "<p><a href='seller-orders'>View Seller Orders</a></p>"
        );

        response.getWriter().println(
            "<p><a href='seller-login'>Seller Login</a></p>"
        );

        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }


    // =========================
    // ADD / EDIT / DELETE
    // =========================
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Get current session
        HttpSession session = request.getSession(false);

        // Check Seller Login
        if (session == null ||
            !"Seller".equals(session.getAttribute("role"))) {

            response.getWriter().println(
                "<h2>Seller Login Required!</h2>"
            );

            return;
        }

        // Seller ID
        int sellerId = 11;

        // Get action
        String action = request.getParameter("action");

        ProductDAO productDAO = new ProductDAO();


        // =========================
        // ADD PRODUCT
        // =========================
        if ("add".equals(action)) {

            String name = request.getParameter("name");

            String description =
                request.getParameter("description");

            double price =
                Double.parseDouble(
                    request.getParameter("price")
                );

            String category =
                request.getParameter("category");

            int stock =
                Integer.parseInt(
                    request.getParameter("stock")
                );

            Product product = new Product(
                0,
                name,
                description,
                price,
                category,
                stock,
                sellerId
            );

            productDAO.addProduct(product);

            response.getWriter().println(
                "<h2>Product Added Successfully!</h2>"
            );
        }


        // =========================
        // EDIT PRODUCT
        // =========================
        else if ("edit".equals(action)) {

            int id =
                Integer.parseInt(
                    request.getParameter("id")
                );

            String name =
                request.getParameter("name");

            String description =
                request.getParameter("description");

            double price =
                Double.parseDouble(
                    request.getParameter("price")
                );

            String category =
                request.getParameter("category");

            int stock =
                Integer.parseInt(
                    request.getParameter("stock")
                );

            Product product = new Product(
                id,
                name,
                description,
                price,
                category,
                stock,
                sellerId
            );

            productDAO.updateProduct(product);

            response.getWriter().println(
                "<h2>Product Updated Successfully!</h2>"
            );
        }


        // =========================
        // DELETE PRODUCT
        // =========================
        else if ("delete".equals(action)) {

            int id =
                Integer.parseInt(
                    request.getParameter("id")
                );

            productDAO.deleteProduct(id, sellerId);

            response.getWriter().println(
                "<h2>Product Deleted Successfully!</h2>"
            );
        }


        // =========================
        // INVALID ACTION
        // =========================
        else {

            response.getWriter().println(
                "<h2>Invalid Product Action!</h2>"
            );
        }

        response.getWriter().println(
            "<p><a href='seller-product'>Back to Manage Products</a></p>"
        );
    }
}