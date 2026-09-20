import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ProductDAO productDAO = new ProductDAO();
        CartDAO cartDAO = new CartDAO();
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        OrderDAO orderDAO = new OrderDAO();

        int userId = 1;

        while (true) {

            System.out.println("\n==============================");
            System.out.println("       FASHION STORE");
            System.out.println("==============================");
            System.out.println("1. View Products");
            System.out.println("2. Search Product");
            System.out.println("3. Filter by Category");
            System.out.println("4. Add to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Checkout");
            System.out.println("7. Order History");
            System.out.println("8. Exit");
            System.out.println("==============================");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                // VIEW PRODUCTS
                case 1:

                    List<Product> products = productDAO.getAllProducts();

                    if (products.isEmpty()) {
                        System.out.println("No products found!");
                    } else {

                        for (Product product : products) {

                            System.out.println("----------------------");
                            System.out.println("Product ID: " + product.id);
                            System.out.println("Name: " + product.name);
                            System.out.println("Description: " + product.description);
                            System.out.println("Price: ₹" + product.price);
                            System.out.println("Category: " + product.category);
                            System.out.println("Stock: " + product.stock);
                        }

                        System.out.println("----------------------");
                    }

                    break;

                // SEARCH PRODUCT
                case 2:

                    sc.nextLine();

                    System.out.print("Enter product name: ");
                    String keyword = sc.nextLine();

                    List<Product> searchResults =
                            productDAO.searchProducts(keyword);

                    if (searchResults.isEmpty()) {
                        System.out.println("No products found!");
                    } else {

                        for (Product product : searchResults) {

                            System.out.println("----------------------");
                            System.out.println("Product ID: " + product.id);
                            System.out.println("Name: " + product.name);
                            System.out.println("Description: " + product.description);
                            System.out.println("Price: ₹" + product.price);
                            System.out.println("Category: " + product.category);
                            System.out.println("Stock: " + product.stock);
                        }

                        System.out.println("----------------------");
                    }

                    break;

                // FILTER CATEGORY
                case 3:

                    sc.nextLine();

                    System.out.print("Enter category: ");
                    String category = sc.nextLine();

                    List<Product> categoryResults =
                            productDAO.filterByCategory(category);

                    if (categoryResults.isEmpty()) {
                        System.out.println("No products found in this category!");
                    } else {

                        for (Product product : categoryResults) {

                            System.out.println("----------------------");
                            System.out.println("Product ID: " + product.id);
                            System.out.println("Name: " + product.name);
                            System.out.println("Description: " + product.description);
                            System.out.println("Price: ₹" + product.price);
                            System.out.println("Category: " + product.category);
                            System.out.println("Stock: " + product.stock);
                        }

                        System.out.println("----------------------");
                    }

                    break;

                // ADD TO CART
                case 4:

                    System.out.print("Enter Product ID: ");
                    int productId = sc.nextInt();

                    System.out.print("Enter Quantity: ");
                    int quantity = sc.nextInt();

                    cartDAO.addToCart(userId, productId, quantity);

                    break;

                // VIEW CART
                case 5:

                    List<CartItem> cartItems =
                            cartDAO.getCartItems(userId);

                    if (cartItems.isEmpty()) {

                        System.out.println("Cart is empty!");

                    } else {

                        for (CartItem item : cartItems) {

                            System.out.println("----------------------");
                            System.out.println("Cart Item ID: " + item.id);
                            System.out.println("Product ID: " + item.productId);
                            System.out.println("Quantity: " + item.quantity);
                        }

                        System.out.println("----------------------");

                        // DISPLAY CART TOTAL
                        double cartTotal = cartDAO.getCartTotal(userId);

                        System.out.println("Cart Total: ₹" + cartTotal);
                    }

                    break;

                // CHECKOUT
                case 6:

                    checkoutDAO.checkout(userId);

                    break;

                // ORDER HISTORY
                case 7:

                    orderDAO.viewOrderHistory(userId);

                    break;

                // EXIT
                case 8:

                    System.out.println("Thank you for using Fashion Store!");

                    sc.close();

                    return;

                default:

                    System.out.println("Invalid choice!");
            }
        }
    }
}