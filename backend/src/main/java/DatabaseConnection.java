import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() {

        String url = "jdbc:sqlserver://localhost:1433;"
                   + "databaseName=FashionStoreDB;"
                   + "trustServerCertificate=true";

        String username = "fashionuser";
        String password = "asmi@2008";

        try {

            // Load SQL Server JDBC Driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Connect to SQL Server
            Connection con = DriverManager.getConnection(
                    url,
                    username,
                    password
            );

            System.out.println("Database Connected Successfully!");

            return con;

        } catch (Exception e) {

            System.out.println("Database Connection Failed!");

            e.printStackTrace();

            return null;
        }
    }

    // Database connection test
    public static void main(String[] args) {

        Connection con = getConnection();

        if (con != null) {

            System.out.println("Connection Test Successful!");

            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {

            System.out.println("Connection Test Failed!");
        }
    }
}