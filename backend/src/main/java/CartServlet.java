import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(true);

        if (session.getAttribute("userId") == null) {
            response.getWriter().println(
                "<h2>Please login first!</h2>"
            );
            return;
        }

        int userId = (int) session.getAttribute("userId");

        CartDAO cartDAO = new CartDAO();

        List<CartItem> cartItems = cartDAO.getCartItems(userId);
        double total = cartDAO.getCartTotal(userId);

        StringBuilder html = new StringBuilder();

        html.append("""
        <!DOCTYPE html>
        <html lang="en">
        <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>AsmiMart - My Cart</title>

        <style>
        *{box-sizing:border-box;margin:0;padding:0}

        body{
            font-family:Arial,Helvetica,sans-serif;
            background:#f6f5fb;
            color:#24243f;
        }

        .header{
            background:linear-gradient(135deg,#5b2be0,#e83e8c);
            color:white;
            padding:18px 6%;
            display:flex;
            align-items:center;
            justify-content:space-between;
            gap:20px;
            flex-wrap:wrap;
            box-shadow:0 5px 20px rgba(80,40,150,.18);
        }

        .logo{
            font-size:30px;
            font-weight:800;
            letter-spacing:.5px;
        }

        .logo span{
            color:#ffd166;
        }

        .nav{
            display:flex;
            gap:22px;
            flex-wrap:wrap;
        }

        .nav a{
            color:white;
            text-decoration:none;
            font-weight:600;
        }

        .nav a:hover{
            color:#ffd166;
        }

        .page{
            width:90%;
            max-width:1200px;
            margin:45px auto;
        }

        .heading{
            text-align:center;
            margin-bottom:35px;
        }

        .heading small{
            color:#e83e8c;
            font-weight:800;
            letter-spacing:2px;
        }

        .heading h1{
            font-size:42px;
            margin:8px 0;
            color:#24243f;
        }

        .heading p{
            color:#777;
            font-size:16px;
        }

        .empty{
            background:white;
            border-radius:22px;
            padding:70px 30px;
            text-align:center;
            box-shadow:0 10px 35px rgba(50,40,100,.10);
        }

        .empty-icon{
            font-size:60px;
            margin-bottom:18px;
        }

        .empty h2{
            margin-bottom:10px;
        }

        .empty p{
            color:#777;
            margin-bottom:25px;
        }

        .btn{
            display:inline-block;
            text-decoration:none;
            border:0;
            border-radius:10px;
            padding:13px 22px;
            font-weight:700;
            cursor:pointer;
        }

        .primary{
            background:linear-gradient(135deg,#5b2be0,#e83e8c);
            color:white;
        }

        .primary:hover{
            opacity:.9;
        }

        .cart-layout{
            display:grid;
            grid-template-columns:1fr 330px;
            gap:28px;
            align-items:start;
        }

        .cart-card{
            background:white;
            border-radius:20px;
            overflow:hidden;
            box-shadow:0 10px 35px rgba(50,40,100,.10);
        }

        .cart-title{
            padding:22px 25px;
            border-bottom:1px solid #eee;
            font-size:20px;
            font-weight:800;
        }

        .item{
            display:grid;
            grid-template-columns:90px 1fr 120px 150px 100px;
            gap:18px;
            align-items:center;
            padding:22px;
            border-bottom:1px solid #eee;
        }

        .item:last-child{
            border-bottom:0;
        }

        .item-image{
            width:90px;
            height:90px;
            border-radius:14px;
            background:#f0eff7;
            overflow:hidden;
        }

        .item-image img{
            width:100%;
            height:100%;
            object-fit:cover;
        }

        .item-info h3{
            font-size:17px;
            margin-bottom:7px;
        }

        .item-info span{
            color:#777;
            font-size:13px;
        }

        .item-price{
            font-weight:800;
            color:#d93687;
            font-size:17px;
        }

        .qty-form{
            display:flex;
            align-items:center;
            gap:7px;
        }

        .qty-form input{
            width:58px;
            padding:10px;
            border:1px solid #ddd;
            border-radius:8px;
            text-align:center;
        }

        .update-btn{
            border:0;
            background:#5b2be0;
            color:white;
            padding:10px 12px;
            border-radius:8px;
            font-weight:700;
            cursor:pointer;
        }

        .remove-btn{
            border:0;
            background:#ffe7ed;
            color:#d62955;
            padding:10px 13px;
            border-radius:8px;
            font-weight:700;
            cursor:pointer;
        }

        .remove-btn:hover{
            background:#ffd4df;
        }

        .summary{
            background:white;
            border-radius:20px;
            padding:25px;
            box-shadow:0 10px 35px rgba(50,40,100,.10);
            position:sticky;
            top:20px;
        }

        .summary h2{
            margin-bottom:22px;
            font-size:22px;
        }

        .summary-row{
            display:flex;
            justify-content:space-between;
            padding:12px 0;
            color:#666;
        }

        .summary-total{
            border-top:1px solid #eee;
            margin-top:10px;
            padding-top:18px;
            display:flex;
            justify-content:space-between;
            font-size:22px;
            font-weight:800;
            color:#24243f;
        }

        .summary-total span:last-child{
            color:#d93687;
        }

        .checkout{
            display:block;
            width:100%;
            text-align:center;
            margin-top:22px;
            padding:15px;
            border-radius:11px;
            background:linear-gradient(135deg,#ff7a00,#e83e8c);
            color:white;
            text-decoration:none;
            font-weight:800;
        }

        .checkout:hover{
            opacity:.92;
        }

        .continue{
            display:block;
            text-align:center;
            margin-top:15px;
            color:#5b2be0;
            text-decoration:none;
            font-weight:700;
        }

        .footer{
            margin-top:60px;
            padding:30px 6%;
            background:#24243f;
            color:#ccc;
            text-align:center;
        }

        .footer strong{
            color:#ffd166;
            font-size:20px;
        }

        @media(max-width:900px){
            .cart-layout{
                grid-template-columns:1fr;
            }

            .item{
                grid-template-columns:75px 1fr;
            }

            .item-image{
                width:75px;
                height:75px;
            }

            .item-price,
            .qty-form,
            .remove-form{
                grid-column:2;
            }

            .summary{
                position:static;
            }
        }

        @media(max-width:600px){
            .heading h1{
                font-size:32px;
            }

            .nav{
                gap:12px;
            }

            .page{
                width:94%;
            }
        }
        </style>
        </head>

        <body>

        <header class="header">
            <div class="logo">Asmi<span>Mart</span></div>

            <nav class="nav">
                <a href="http://127.0.0.1:5500/frontend/home.html">Home</a>
                <a href="http://127.0.0.1:5500/frontend/products.html">Shop</a>
                <a href="http://localhost:8080/fashionstore-backend/cart">Cart</a>
                <a href="http://127.0.0.1:5500/frontend/index.html">Login</a>
            </nav>
        </header>

        <main class="page">

            <div class="heading">
                <small>YOUR SHOPPING BAG</small>
                <h1>My Shopping Cart</h1>
                <p>Review your selected fashion items before checkout.</p>
            </div>
        """);

        if (cartItems == null || cartItems.isEmpty()) {

            html.append("""
                <div class="empty">
                    <div class="empty-icon">🛒</div>
                    <h2>Your cart is empty</h2>
                    <p>Looks like you haven't added anything to your cart yet.</p>
                    <a class="btn primary"
                       href="http://127.0.0.1:5500/frontend/products.html">
                       Continue Shopping
                    </a>
                </div>
            """);

        } else {

            html.append("""
                <div class="cart-layout">

                    <div class="cart-card">

                        <div class="cart-title">
                            🛍️ Selected Items
                        </div>
            """);

            for (CartItem item : cartItems) {

                String name = getProductName(item.productId);
                String image = getProductImage(item.productId);
                double price = getProductPrice(item.productId);
                double subtotal = price * item.quantity;

                html.append(
                    "<div class='item'>" +

                        "<div class='item-image'>" +
                            "<img src='http://127.0.0.1:5500/frontend/" +
                            image + "' alt='" + name + "'>" +
                        "</div>" +

                        "<div class='item-info'>" +
                            "<h3>" + name + "</h3>" +
                            "<span>Product ID: " + item.productId + "</span>" +
                        "</div>" +

                        "<div class='item-price'>" +
                            "₹" + String.format("%.2f", subtotal) +
                        "</div>" +

                        "<div>" +
                            "<form class='qty-form' method='post' action='cart'>" +

                            "<input type='hidden' name='action' value='update'>" +

                            "<input type='hidden' name='id' value='" +
                            item.id + "'>" +

                            "<input type='number' name='quantity' " +
                            "value='" + item.quantity + "' min='1'>" +

                            "<button class='update-btn' type='submit'>" +
                            "Update</button>" +

                            "</form>" +
                        "</div>" +

                        "<div class='remove-form'>" +
                            "<form method='post' action='cart'>" +

                            "<input type='hidden' name='action' value='remove'>" +

                            "<input type='hidden' name='id' value='" +
                            item.id + "'>" +

                            "<button class='remove-btn' type='submit'>" +
                            "Remove</button>" +

                            "</form>" +
                        "</div>" +

                    "</div>"
                );
            }

            html.append(
                "</div>" +

                "<aside class='summary'>" +

                    "<h2>Order Summary</h2>" +

                    "<div class='summary-row'>" +
                        "<span>Items</span>" +
                        "<span>" + cartItems.size() + "</span>" +
                    "</div>" +

                    "<div class='summary-row'>" +
                        "<span>Delivery</span>" +
                        "<span>FREE</span>" +
                    "</div>" +

                    "<div class='summary-total'>" +
                        "<span>Total</span>" +
                        "<span>₹" +
                        String.format("%.2f", total) +
                        "</span>" +
                    "</div>" +

                    "<a class='checkout' href='checkout'>" +
                        "Proceed to Checkout →" +
                    "</a>" +

                    "<a class='continue' " +
                    "href='http://127.0.0.1:5500/frontend/products.html'>" +
                        "← Continue Shopping" +
                    "</a>" +

                "</aside>" +

                "</div>"
            );
        }

        html.append("""
        </main>

        <footer class="footer">
            <strong>AsmiMart</strong>
            <p>Modern Fashion. Better Style. Smarter Shopping.</p>
            <p>© 2026 AsmiMart. All Rights Reserved.</p>
        </footer>

        </body>
        </html>
        """);

        response.getWriter().println(html.toString());
    }


    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(true);

        if (session.getAttribute("userId") == null) {
            response.getWriter().println(
                "<h2>Please login first!</h2>"
            );
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String action = request.getParameter("action");

        CartDAO cartDAO = new CartDAO();

        try {

            if ("add".equals(action)) {

                int productId = Integer.parseInt(
                    request.getParameter("productId")
                );

                int quantity = Integer.parseInt(
                    request.getParameter("quantity")
                );

                cartDAO.addToCart(
                    userId,
                    productId,
                    quantity
                );

            } else if ("remove".equals(action)) {

                int id = Integer.parseInt(
                    request.getParameter("id")
                );

                cartDAO.removeFromCart(id);

            } else if ("update".equals(action)) {

                int id = Integer.parseInt(
                    request.getParameter("id")
                );

                int quantity = Integer.parseInt(
                    request.getParameter("quantity")
                );

                cartDAO.updateQuantity(
                    id,
                    quantity
                );
            }

            response.sendRedirect(
                request.getContextPath() + "/cart"
            );

        } catch (Exception e) {

            response.setContentType(
                "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                "<h2>Cart operation failed!</h2>"
            );

            e.printStackTrace();
        }
    }


    private String getProductName(int productId) {

        switch (productId) {

            case 2:
                return "T-Shirt";

            case 5:
                return "Classic Shirt";

            case 6:
                return "Denim Jeans";

            case 7:
                return "Women Dress";

            case 8:
                return "Women Top";

            case 9:
                return "Kids T-Shirt";

            case 10:
                return "Kids Dress";

            case 11:
                return "Running Shoes";

            case 12:
                return "Fashion Handbag";

            case 13:
                return "Leather Belt";

            default:
                return "Fashion Product";
        }
    }


    private String getProductImage(int productId) {

        switch (productId) {

            case 2:
                return "images/tshirt.png";

            case 5:
                return "images/shirt.png";

            case 6:
                return "images/jeans.png";

            case 7:
                return "images/womendress.png";

            case 8:
                return "images/womentop.png";

            case 9:
                return "images/kidstshirt.png";

            case 10:
                return "images/kidsdress.png";

            case 11:
                return "images/shoes.png";

            case 12:
                return "images/handbag.png";

            case 13:
                return "images/belt.png";

            default:
                return "images/tshirt.png";
        }
    }


    private double getProductPrice(int productId) {

        switch (productId) {

            case 2:
                return 499.00;

            case 5:
                return 799.00;

            case 6:
                return 1299.00;

            case 7:
                return 999.00;

            case 8:
                return 699.00;

            case 9:
                return 399.00;

            case 10:
                return 599.00;

            case 11:
                return 1499.00;

            case 12:
                return 1199.00;

            case 13:
                return 499.00;

            default:
                return 0.00;
        }
    }
}