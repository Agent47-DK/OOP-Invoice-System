package Components.Invoice;

import Entities.Customer;
import Entities.Invoice;
import Entities.Product;
import Helper.Utils;
import Services.CustomerService;
import Services.DbConnectionService;
import Services.InvoiceService;
import Services.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CreateInvoice {
    private JPanel CreateInvoicePanel;
    private JComboBox customerComboBox;
    private JTextField dueDateTxtField;
    private JComboBox productComboBox;
    private JTextField quantityTextField;
    private JButton addItemButton;
    private JButton deleteItemButton;
    private JTable invoiceItemsTable;
    private JTextField totalTxtField;
    private JButton saveInvoiceButton;
    private JButton cancelButton;
    private JLabel createInvoiceTitleLabel;
    private JLabel selectCustomerLabel;
    private JLabel selectProductLabel;
    private JLabel dateDueLabel;
    private JLabel quantityLabel;
    private JLabel totalAmountLabel;

    private JFrame frame;

    private List<Product> products; // we need to have an list of products
    private double totalAmount = 0;

    //private  Object[] row = new Object[]{};
    private ArrayList<Object[]> rows = new ArrayList<>();

    public CreateInvoice() {
        initialise();
        getCustomers();
        getProducts();
    }

    private void initialise() {
        frame = new JFrame();
        this.frame.setTitle("Create invoice");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItem();
            }
        });

        saveInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveInvoice();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Invoices invoices = new Invoices();
            }
        });

        this.frame.setContentPane(CreateInvoicePanel);
        this.frame.setVisible(true);
    }

    private void getCustomers() {
        try {
            // Get list of customers
            CustomerService cust = new CustomerService();
            List<Customer> customers = cust.getAllCustomers();
            DbConnectionService.closeConnection();
            for(Customer customer : customers) {
                this.customerComboBox.addItem(customer.getName());
            }
        }catch (Exception e) {
            // Handle any other exceptions that might occur
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void getProducts() {
        try {
            // get list of products
            ProductService prod = new ProductService();
            this.products = prod.getAllProducts();
            DbConnectionService.closeConnection();
            for(Product product : products) {
                // Only products we are selling to customer
                if(String.valueOf(product.getCategory()).equals("sell")) {
                    this.productComboBox.addItem(product.getName());
                }

            }
        }catch (Exception e) {
            // Handle any other exceptions that might occur
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: " + e.getMessage(),
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }


    private void addItem() {
        // Get the selected customer, product, and quantity
        String customer = (String) customerComboBox.getSelectedItem();
        String product = (String) productComboBox.getSelectedItem();
        String quantity = quantityTextField.getText();

        // validations
        if (customer.isEmpty() || product.isEmpty() || quantity.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Customer, Product & Quantity need to be set",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int quantityNum = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame,
                    "Quantity must be an integer",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Find the product that matches the selected product name
        for (Product prod : this.products) {
            if (prod.getName().equals(product)) {
                // Create a new array for the row
                Object[] newRow = new Object[]{prod.getName(), prod.getDescription(), quantity, prod.getPrice()};

                // Add the row to the ArrayList
                rows.add(newRow);

                this.totalAmount += prod.getPrice() * Double.parseDouble(quantity);
            }
        }

        // Convert the ArrayList to a 2D array
        Object[][] data = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        // Set the data to the table model
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"Company Name", "Description", "Quantity", "Price"});
        invoiceItemsTable.setModel(model);

        // Update the total amount
        totalTxtField.setText(String.valueOf(this.totalAmount));
    }

    private void removeItem() {
        // Get the selected row index
        int rowIndex = invoiceItemsTable.getSelectedRow();

        // Check if a row is selected
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(frame,
                    "Please select a row to remove",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the product name and quantity of the selected row
        String productName = (String) invoiceItemsTable.getValueAt(rowIndex, 0);
        String quantity = (String) invoiceItemsTable.getValueAt(rowIndex, 2);

        // Remove the row from the ArrayList
        for (int i = 0; i < rows.size(); i++) {
            Object[] row = rows.get(i);
            String name = (String) row[0];
            if (name.equals(productName)) {
                rows.remove(i);
                break;
            }
        }

        // Convert the ArrayList to a 2D array
        Object[][] data = new Object[rows.size()][];
        for (int i = 0; i < rows.size(); i++) {
            data[i] = rows.get(i);
        }

        // Set the data to the table model
        DefaultTableModel model = new DefaultTableModel(data, new String[]{"Company Name", "Description", "Quantity", "Price"});
        invoiceItemsTable.setModel(model);

        // Update the total amount
        this.totalAmount -= Double.parseDouble(quantity) * getProductPrice(productName);
        totalTxtField.setText(String.valueOf(this.totalAmount));
    }

    private double getProductPrice(String productName) {
        for (Product prod : this.products) {
            if (prod.getName().equals(productName)) {
                return prod.getPrice();
            }
        }
        return 0.0;
    }


    private void saveInvoice() {
        String customer = (String) customerComboBox.getSelectedItem();
        String product = (String) productComboBox.getSelectedItem();
        String quantity = quantityTextField.getText();
        String dueDate = dueDateTxtField.getText();

        System.out.println(dueDate);

        if(!Utils.validateDate(dueDate)){
            JOptionPane.showMessageDialog(frame,
                    "Date format is incorrect: YYYY-MM-DD is required",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        // get customer Id
        CustomerService customerService = new CustomerService();
        Customer cust = customerService.getCustomerByCompanyName(customer);
        DbConnectionService.closeConnection();
        int customerId = cust.getCustomerId();

        //Generate todays date
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = today.format(formatter);



        // validate due date format
        LocalDate dateDue = LocalDate.parse(dueDate, formatter);
        int option = JOptionPane.showConfirmDialog(frame,
                "You are about to issue Invoice ",
                "Confirm Update",
                JOptionPane.YES_NO_OPTION);

        if(option == JOptionPane.OK_OPTION) {
            try{
                // save invoice and get invoiceNumber in return
                InvoiceService invoiceService = new InvoiceService();
                Invoice invoice = invoiceService.createInvoice(formattedDate, this.totalAmount, dateDue, customerId);
                DbConnectionService.closeConnection();
                if(invoice.getInvoiceId() > 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Invoice " + invoice.getInvoiceId() + " has been successfully added",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                    DefaultTableModel model =  (DefaultTableModel) invoiceItemsTable.getModel();
                    int rowCount = model.getRowCount();
                    // save to invoice_product db
                    for (int i = 0; i < rowCount; i++) {
                        String productName = (String) model.getValueAt(i, 0);
                        int quantyty = Integer.parseInt((String) model.getValueAt(i, 2));
                        // get product id
                        ProductService productService = new ProductService();
                        Product product1 = productService.getProductByName(productName);
                        DbConnectionService.closeConnection();

                        // Save prod_invoice
                        InvoiceService inv_prod = new InvoiceService();
                        inv_prod.addInvoiceProduct(invoice.getInvoiceId(), product1.getProductId(), quantyty);
                        DbConnectionService.closeConnection();
                    }
                }
                frame.dispose();
                CreateInvoice createInvoice = new CreateInvoice();
            }catch(Exception e) {
                // Handle any other exceptions that might occur
                JOptionPane.showMessageDialog(frame,
                        "An unexpected error occurred: \n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
