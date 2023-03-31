package Services;
/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class db connections. Note that we use static variable and method here. We only need one instance
 *      of the connection object to ensure that only one connection is made to the database to avoid connection conflicts
 *      Also the close connection is static so that we can call it without creating an instance of the class
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionService {

    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // Create a new database connection
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost/c00290922";
                String user = "root";
                String password = "";
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static void closeConnection() {
        // Close the database connection
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
