 
    
public class CartItem {

    int id;
    int userId;
    int productId;
    int quantity;

    public CartItem(int id, int userId, int productId, int quantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
