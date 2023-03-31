/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class handles all invoice related interactions with the database
 */
package Services;

import Entities.InvCust;
import Entities.Invoice;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceService {
    private JFrame frame;
    private Connection connection;

    public InvoiceService() {
        connection = DbConnectionService.getConnection();
    }

    public Invoice createInvoice(String todayDate, double totalAmount, LocalDate dateDue, int customerId) {
        Invoice invoice = new Invoice();
        try {
            int deleted = 0; // new product deleted attribute set to 0
            Statement smt = connection.createStatement();

            // get id
            String query = "SELECT invoiceId FROM invoice ORDER BY invoiceId DESC LIMIT 1";
            ResultSet resultSet = smt.executeQuery(query);

            // get last invoice entered id if no invoice set it to zero
            int lastInvoiceId = resultSet.next() ? resultSet.getInt("invoiceId") : 0;

            // set new invoiceId
            int newInvoiceId = lastInvoiceId + 1;

            //placeholder values
            String status = "draft";
            double amountPaid = 0;

            // Insert new invoice to database
            String sql = "INSERT INTO invoice (invoiceId, dateCreated, amountDue, status, dueDate, totalCost, amountPaid, customerId, deleted)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, newInvoiceId);
            statement.setString(2, todayDate);
            statement.setDouble(3, totalAmount);
            statement.setString(4, status);
            statement.setString(5, String.valueOf(dateDue));
            statement.setDouble(6, totalAmount);
            statement.setDouble(7, amountPaid);
            statement.setInt(8, customerId);
            statement.setInt(9, deleted);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                invoice.setInvoiceId(newInvoiceId);
                invoice.setDateCreated(todayDate);
                invoice.setAmountDue(totalAmount);
                invoice.setStatus(Invoice.Status.valueOf(status));
                invoice.setDueDate(dateDue);
                invoice.setTotalCost(totalAmount);
                invoice.setAmountPaid(amountPaid);
                invoice.setCustomerId(customerId);
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
        return invoice;
    }

    public void addInvoiceProduct(int invoiceId, int productId, int quantity) {
        try{
            Statement smt = connection.createStatement();

            // get id
            String query = "SELECT invoiceProductId FROM invoice_product ORDER BY invoiceProductId DESC LIMIT 1";
            ResultSet resultSet = smt.executeQuery(query);

            // get last invoice_prod entered id if no invoice_prod set it to zero
            int lastInvoiceId = resultSet.next() ? resultSet.getInt("invoiceProductId") : 0;

            // set new invoiceId
            int newInvoiceId = lastInvoiceId + 1;

            // Insert new invoice_product to database
            String sql = "INSERT INTO invoice_product (invoiceProductId, invoiceId, productId, quantity)" +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, newInvoiceId);
            statement.setInt(2, invoiceId);
            statement.setInt(3, productId);
            statement.setInt(4, quantity);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted < 1) {
                JOptionPane.showMessageDialog(frame,
                        "Could not insert values: ",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
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

    }

    public List<InvCust> getInvoices() {
        List<InvCust> invCusts = new ArrayList<>();
        try{
            String sql = "SELECT invoice.invoiceId, invoice.dateCreated, invoice.amountDue, customer.name, customer.email " +
                    "FROM invoice " +
                    "INNER JOIN customer ON invoice.customerId = customer.customerId " +
                    "WHERE invoice.customerId = customer.customerId";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                InvCust invCust = new InvCust();
                invCust.setInvoiceId(rs.getInt("invoiceId"));
                invCust.setDateCreated(String.valueOf(rs.getDate("dateCreated")));
                invCust.setAmountDue(rs.getDouble("amountDue"));
                invCust.setName(rs.getString("name"));
                invCust.setEmail(rs.getString("email"));
                invCusts.add(invCust);
                // display the retrieved data in a Java Swing component, such as a table or list
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

        return invCusts;
    }

}
