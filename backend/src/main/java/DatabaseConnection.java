import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {

        String url = System.getenv("DATABASE_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        try {
            if (url == null || username == null || password == null) {
                throw new SQLException(
                    "Database environment variables are missing!"
                );
            }

            // Convert Render URL to JDBC URL
            if (url.startsWith("postgresql://")) {
                url = url.replaceFirst(
                    "^postgresql://", "jdbc:postgresql://"
                );
            }

            // Enable SSL for Render PostgreSQL
            if (!url.contains("sslmode=")) {
                url += url.contains("?") ? "&sslmode=require"
                                         : "?sslmode=require";
            }

            Class.forName("org.postgresql.Driver");

            Connection con = DriverManager.getConnection(
                url, username, password
            );

            System.out.println("PostgreSQL Connected Successfully!");
            return con;

        } catch (Exception e) {
            System.out.println("Database Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        try (Connection con = getConnection()) {
            if (con != null) {
                System.out.println("Connection Test Successful!");
            }
        } catch (SQLException e) {
            System.out.println("Connection Test Failed!");
            e.printStackTrace();
        }
    }
}