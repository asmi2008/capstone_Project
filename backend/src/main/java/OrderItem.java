 
    
public class OrderItem {

    int id;
    int orderId;
    int productId;
    int quantity;
    double price;

    public OrderItem(int id, int orderId, int productId,
                     int quantity, double price) {

        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }
}
