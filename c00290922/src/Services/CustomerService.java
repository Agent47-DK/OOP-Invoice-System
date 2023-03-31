/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class handles all customer related interactions with the database
 */
package Services;

import Entities.Customer;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private JFrame frame;
    private Connection connection;

    public CustomerService() {
        connection = DbConnectionService.getConnection();
    }

    public Customer addCustomer(String name, String firstname, String lastname, String email, String contactNo, String website, String address){
        Customer customer = new Customer();
        try{
            int deleted = 0; // new customer deleted attribute set to 0
            Statement smt = connection.createStatement();
            // get id
            String query = "SELECT customerId FROM customer ORDER BY customerId DESC LIMIT 1";
            ResultSet resultSet = smt.executeQuery(query);

            // get last customer entered id if no customer set it to zero
            int lastCustomerId = resultSet.next() ? resultSet.getInt("customerId") : 0;

            // set new customerId
            int newCustomerId = lastCustomerId + 1;

            // Insert new customer to database
            String sql = "INSERT INTO customer (customerId, name, firstname, lastname, email, contactNo, website, address, deleted)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, newCustomerId);
            statement.setString(2, name);
            statement.setString(3, firstname);
            statement.setString(4, lastname);
            statement.setString(5, email);
            statement.setString(6, contactNo);
            statement.setString(7, website);
            statement.setString(8, address);
            statement.setInt(9, deleted);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                customer.setCustomerId(newCustomerId);
                customer.setName(name);
                customer.setFirstname(firstname);
                customer.setLastname(lastname);
                customer.setEmail(email);
                customer.setContactNo(contactNo);
                customer.setWebsite(website);
                customer.setAddress(address);
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

        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            Statement smt = connection.createStatement();
            String query = "SELECT * FROM customer WHERE deleted = 0";
            ResultSet resultSet = smt.executeQuery(query);

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(resultSet.getInt("customerId"));
                customer.setName(resultSet.getString("name"));
                customer.setFirstname(resultSet.getString("firstname"));
                customer.setLastname(resultSet.getString("lastname"));
                customer.setEmail(resultSet.getString("email"));
                customer.setContactNo(resultSet.getString("contactNo"));
                customer.setWebsite(resultSet.getString("website"));
                customer.setAddress(resultSet.getString("address"));
                customers.add(customer);
            }
        }catch (SQLException e) {
            // Handle SQL exceptions, such as invalid syntax, duplicate keys, etc.
            JOptionPane.showMessageDialog(frame,
                    "Error executing SQL statement: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);

        } catch (Exception e) {
            // Handle any other exceptions that might occur
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

        return customers;
    }

    public Customer getCustomerByCompanyName(String companyName) {
        Customer customer = new Customer();
        try{
            // best to use prepared statement ? to avoid injection vulnerability and prevent security issues
            // this is called  parameterized SQL query
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE deleted = 0 AND name = ?");
            preparedStatement.setString(1, companyName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                customer.setCustomerId(resultSet.getInt("customerId"));
                customer.setName(resultSet.getString("name"));
                customer.setFirstname(resultSet.getString("firstname"));
                customer.setLastname(resultSet.getString("lastname"));
                customer.setEmail(resultSet.getString("email"));
                customer.setContactNo(resultSet.getString("contactNo"));
                customer.setWebsite(resultSet.getString("website"));
                customer.setAddress(resultSet.getString("address"));
            }

        }catch (SQLException e) {
            // Handle SQL exceptions, such as invalid syntax, duplicate keys, etc.
            JOptionPane.showMessageDialog(frame,
                    "Error executing SQL statement: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);

        }catch (Exception e) {
            // Handle any other exceptions that might occur
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

        return customer;
    }

    public boolean updateCustomer(Customer customer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET name = ?, firstname = ?, lastname = ?, email = ?, contactNo = ?, website = ?, address = ? WHERE customerId = ?");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getFirstname());
            preparedStatement.setString(3, customer.getLastname());
            preparedStatement.setString(4, customer.getEmail());
            preparedStatement.setString(5, customer.getContactNo());
            preparedStatement.setString(6, customer.getWebsite());
            preparedStatement.setString(7, customer.getAddress());
            preparedStatement.setInt(8, customer.getCustomerId());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            // Handle SQL exceptions, such as invalid syntax, duplicate keys, etc.
            JOptionPane.showMessageDialog(frame,
                    "Error executing SQL statement: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Handle any other exceptions that might occur
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }

    public boolean deleteCustomer(String companyName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE customer SET deleted = 1 WHERE name = ?");
            preparedStatement.setString(1, companyName);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            }
        } catch (SQLException e) {
            // Handle SQL exceptions, such as invalid syntax, duplicate keys, etc.
            JOptionPane.showMessageDialog(frame,
                    "Error executing SQL statement: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            // Handle any other exceptions that might occur
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

        return false;
    }



}
