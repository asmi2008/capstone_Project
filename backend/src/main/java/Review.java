public class Review {

    int id;
    int productId;
    int userId;
    int rating;
    String comment;

    public Review(int id, int productId, int userId,
                  int rating, String comment) {

        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }
}