package Components;

import Entities.Customer;
import Helper.Utils;
import Services.CustomerService;
import Services.DbConnectionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Dashboard {
    private JPanel MainPanel;
    private JPanel DashboardPanel;
    private JPanel MenuPanel;
    private JLabel MenuLabel;
    private JLabel addCustomerLabel;
    private JLabel ammendCustomerLabel;
    private JLabel deleteCustomerLabel;
    private JLabel createInvoiceLabel;
    private JLabel ammendInvoiceLabel;
    private JLabel deleteInvoiceLabel;
    private JLabel addProductLabel;
    private JLabel ammendProductLabel;
    private JLabel deleteProductLabel;
    private JLabel createBillLabel;
    private JLabel ammendBillLabel;
    private JLabel deleteBill;
    private JLabel logOffLabel;
    private JPanel addCustomerPanel;
    private JLabel addCustomerlbl;
    private JPanel AmmendCustomerPanel;
    private JPanel addCustomerFormPanel;
    private JTextField companyNameTxtField;
    private JTextField firstNameTxtField;
    private JTextField lastnameTxtField;
    private JTextField customerEmailTxtField;
    private JTextField customerContactTxtField;
    private JTextField customerWebsiteTxtField;
    private JTextArea addressTxtArea;
    private JLabel companyNamelabel;
    private JLabel firstNameLabel;
    private JLabel lastNamelabel;
    private JLabel CustomerEmailTxtField;
    private JLabel customerContactLabel;
    private JLabel customerWebsiteLabel;
    private JLabel CustomerAddresslabel;
    private JButton AddCustomerBtn;
    private JButton cancelAddButton;
    private JTextField searchTxtField;
    private JButton searchButton;
    private JTable customersTable;


    private JFrame frame;

    public Dashboard() {
        initialise();
    }

    private void initialise() {
        frame = new JFrame();
        this.frame.setTitle("Dashboard");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        addCustomerPanel.setVisible(false);
        AmmendCustomerPanel.setVisible(false);
       // DashboardPanel.setVisible(false);
        MenuPanel.setPreferredSize(new Dimension(200, 300));
        MenuPanel.setMaximumSize(MenuPanel.getPreferredSize());
        MenuPanel.setMinimumSize(MenuPanel.getPreferredSize());


        addCustomerLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //DashboardPanel.setVisible(false); // hide the dashboard panel
                AmmendCustomerPanel.setVisible(false);
                addCustomerPanel.setVisible(true);

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        ammendCustomerLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               // DashboardPanel.setVisible(false); // hide the dashboard panel
                addCustomerPanel.setVisible(false);
                AmmendCustomerPanel.setVisible(true);
                getCustomers();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        AddCustomerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        cancelAddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAdd();
            }
        });
        this.frame.setContentPane(MainPanel);
        this.frame.setVisible(true);

    }

    public void addCustomer() {
        String name = companyNameTxtField.getText();
        String firstname = firstNameTxtField.getText();
        String lastname = lastnameTxtField.getText();
        String email = customerEmailTxtField.getText();
        String contactNo = customerContactTxtField.getText();
        String website = customerWebsiteTxtField.getText();
        String address = addressTxtArea.getText();

        try{
            if (name.isEmpty() || email.isEmpty() || contactNo.isEmpty() || firstname.isEmpty() ||
                    lastname.isEmpty() || website.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(frame,
                        "Please Fill all Fields",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!Utils.validateEmail(email)){
                JOptionPane.showMessageDialog(frame,
                        "Please Enter Email Address In Correct Format",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!Utils.validateContact(contactNo)){
                JOptionPane.showMessageDialog(frame,
                        "Please Enter Contact number in correct format. Only digits, no spaces or + symbol",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(!Utils.validateUrl(website)){
                JOptionPane.showMessageDialog(frame,
                        "Please Enter website url in the correct format e.g www.example.com",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            CustomerService customerService =  new CustomerService();
            Customer customer = customerService.addCustomer(name, firstname, lastname, email, contactNo, website, address);
            DbConnectionService.closeConnection();

            if(customer.getName() != null) {
                JOptionPane.showMessageDialog(frame,
                        "User " + customer.getName() + "has been successfully added",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
                cancelAdd();
            }
        }catch(Exception e) {
            // Handle any other exceptions that might occur
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: \n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void  cancelAdd() {
        companyNameTxtField.setText("");
        firstNameTxtField.setText("");
        lastnameTxtField.setText("");
        customerEmailTxtField.setText("");
        customerContactTxtField.setText("");
        customerWebsiteTxtField.setText("");
        addressTxtArea.setText("");
    }

    public void getCustomers() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("CustomerID");
        model.addColumn("Company Name");
        model.addColumn("First Name");
        model.addColumn("Surname");
        model.addColumn("Email");
        model.addColumn("Contact No");
        model.addColumn("Website");
        model.addColumn("Address");

        // Set table model
        customersTable.setModel(model);

        // Get list of customers
        CustomerService cust = new CustomerService();
        List<Customer> customers = cust.getAllCustomers();
        DbConnectionService.closeConnection();

        for(Customer customer : customers) {
            model.addRow(new Object[]{customer.getCustomerId(), customer.getName(), customer.getFirstname(), customer.getLastname()
            , customer.getEmail(), customer.getContactNo(), customer.getWebsite(), customer.getAddress()});
        }

        // Set the Table model
        customersTable.setModel(model);

    }
}
