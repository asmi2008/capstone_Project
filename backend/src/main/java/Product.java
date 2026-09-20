public class Product {

    int id;
    String name;
    String description;
    double price;
    String category;
    int stock;
    int sellerId;

    public Product(int id, String name, String description,
                   double price, String category, int stock, int sellerId) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.stock = stock;
        this.sellerId = sellerId;
    }
}