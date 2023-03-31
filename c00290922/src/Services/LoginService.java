package Services;

import javax.swing.*;
import java.sql.*;


/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class handles the login request from client and sends the query to the database
 */
public class LoginService {
    private Connection connection;
    private JFrame frame;

    public LoginService() {
        connection = DbConnectionService.getConnection();
    }

    public boolean authenticate(String username, String password) {
        // check if user has been authenticated
        try{
            // Prepare sql query
            String sql = "SELECT COUNT(*) FROM user WHERE BINARY username = ? AND BINARY password = ?"; //Binary for case sensitivity
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Get the table data result
            ResultSet resultSet = statement.executeQuery();
            resultSet.next(); // since the cursor is positioned before the 1st row move it to make 1st row current row
            int count = resultSet.getInt(1);

            if (count == 1) {
                // query has a result
                return true;
            }
        }catch (SQLException e) {
            // Handle SQL exceptions, such as invalid syntax, duplicate keys, etc.
            JOptionPane.showMessageDialog(frame,
                    "Error executing SQL statement: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }  catch (Exception e) {
            // Handle any other exceptions that might occur
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
