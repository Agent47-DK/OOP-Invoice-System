/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class handles the register request from client and sends the query to the database
 */
package Services;

import Entities.User;

import javax.swing.*;
import java.sql.*;



public class RegisterService {
    private JFrame frame;
    private Connection connection;

    public RegisterService() {
        connection = DbConnectionService.getConnection();
    }

    public User addUser(String username, String password, String email, String contactNo, String role) {
        User user = new User();
        try {
            Statement smt = connection.createStatement();
            // get id
            String query = "SELECT userId FROM user ORDER BY userId DESC LIMIT 1";
            ResultSet resultSet = smt.executeQuery(query);

            // get last user entered id if no user set it to null
            int lastUserId = resultSet.next() ? resultSet.getInt("userId") : 0;

            // set new userId
            int newUserId = lastUserId + 1;

            // Insert new user to database
            String sql = "INSERT INTO user (userId, username, password, email, contactNo, role)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, newUserId);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, email);
            statement.setString(5, contactNo);
            statement.setString(6, role);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                user.setUserId(newUserId);
                user.setUsername(username);
                user.setPassword(password);
                user.setEmail(email);
                user.setContactNo(contactNo);
                user.setRole(User.Role.valueOf(role));
            }
        } catch (SQLException e) {
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
        return user;
    }
}
