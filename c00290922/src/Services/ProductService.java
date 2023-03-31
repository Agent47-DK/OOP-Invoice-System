/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class handles all product related interactions with the database
 */
package Services;

import Entities.Product;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class ProductService {
    private JFrame frame;
    private Connection connection;

    public ProductService() {
        connection = DbConnectionService.getConnection();
    }

    public Product addProduct(String name, String description, double price, String category) {
        Product product = new Product();
        try{
            int deleted = 0; // new product deleted attribute set to 0
            Statement smt = connection.createStatement();
            // get id
            String query = "SELECT productId FROM product ORDER BY productId DESC LIMIT 1";
            ResultSet resultSet = smt.executeQuery(query);

            // get last product entered id if no product set it to zero
            int lastProductId = resultSet.next() ? resultSet.getInt("productId") : 0;

            // set new productId
            int newProductId = lastProductId + 1;

            // Insert new product to database
            String sql = "INSERT INTO product (productId, name, description, price, category, deleted)" +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, newProductId);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setDouble(4, price);
            statement.setString(5, category);
            statement.setInt(6, deleted);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                product.setProductId(newProductId);
                product.setName(name);
                product.setDescription(description);
                product.setPrice(price);
                product.setCategory(Product.Category.valueOf(category));

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

        return product;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Statement smt = connection.createStatement();
            String query = "SELECT * FROM product WHERE deleted = 0";
            ResultSet resultSet = smt.executeQuery(query);

            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("productId"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setCategory(Product.Category.valueOf(resultSet.getString("category")));

                products.add(product);
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

        return products;
    }

    public Product getProductByName(String productName) {
        Product product = new Product();
        try{
            // best to use prepared statement ? to avoid injection vulnerability and prevent security issues
            // this is called  parameterized SQL query
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE deleted = 0 AND name = ?");
            preparedStatement.setString(1, productName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product.setProductId(resultSet.getInt("productId"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                product.setCategory(Product.Category.valueOf(resultSet.getString("category")));
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

        return product;
    }
    public boolean updateProduct(Product product) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product SET name = ?, description = ?, price = ?, category = ? WHERE productId = ?");
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, valueOf(product.getCategory()));
            preparedStatement.setInt(5, product.getProductId());

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

    public boolean deleteProduct(String productName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE product SET deleted = 1 WHERE name = ?");
            preparedStatement.setString(1, productName);

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
