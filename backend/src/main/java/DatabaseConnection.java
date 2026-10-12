import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getConnection() {
        String host = System.getenv("DB_HOST");
        String port = System.getenv("DB_PORT");
        String dbName = System.getenv("DB_NAME");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (host == null || port == null || dbName == null
                || username == null || password == null) {
            System.out.println("Database environment variables are missing!");
            return null;
        }

        String url = "jdbc:sqlserver://" + host + ":" + port
                + ";databaseName=" + dbName
                + ";encrypt=true;trustServerCertificate=true"
                + ";loginTimeout=15";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection con = DriverManager.getConnection(
                    url, username, password);

            System.out.println("SQL Server Connected Successfully!");
            return con;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("SQL Server Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
}
