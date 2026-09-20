import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    // ADD REVIEW
    public void addReview(Review review) {

        String sql = "INSERT INTO Reviews " +
                     "(ProductId, UserId, Rating, Comment) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, review.productId);
            ps.setInt(2, review.userId);
            ps.setInt(3, review.rating);
            ps.setString(4, review.comment);

            ps.executeUpdate();

            System.out.println("Review added successfully!");

        } catch (SQLException e) {

            System.out.println("Failed to add review!");
            e.printStackTrace();
        }
    }

    // VIEW REVIEWS FOR A PRODUCT
    public List<Review> getReviewsByProduct(int productId) {

        List<Review> reviews = new ArrayList<>();

        String sql =
            "SELECT Id, ProductId, UserId, Rating, Comment " +
            "FROM Reviews " +
            "WHERE ProductId = ? " +
            "ORDER BY Id DESC";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Review review = new Review(
                    rs.getInt("Id"),
                    rs.getInt("ProductId"),
                    rs.getInt("UserId"),
                    rs.getInt("Rating"),
                    rs.getString("Comment")
                );

                reviews.add(review);
            }

        } catch (SQLException e) {

            System.out.println("Failed to get reviews!");
            e.printStackTrace();
        }

        return reviews;
    }
}