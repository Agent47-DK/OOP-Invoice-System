package Components.Customer;

import Components.Home;
import Entities.Customer;
import Services.CustomerService;
import Services.DbConnectionService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Customers {
    private JPanel CustomersPanel;
    private JTable customersTable;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton addButton;
    private JLabel customersLabel;
    private JLabel homeLabel;

    private JFrame frame;

    public Customers() {
        initialise();
        getCustomers();
    }

    private void initialise() {
        frame = new JFrame();
        this.frame.setTitle("Customers");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCustomer customer = new AddCustomer();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateCustomer customer = new UpdateCustomer();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteCustomer customer = new DeleteCustomer();
            }
        });

        homeLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Home home = new Home();
                frame.dispose();
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

        this.frame.setContentPane(CustomersPanel);
        this.frame.setVisible(true);
    }

    public void getCustomers() {
        // Define table headers

        // Create table model with headers
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Company Name");
        model.addColumn("First Name");
        model.addColumn("Surname");
        model.addColumn("Email");
        model.addColumn("Contact No");



        // Get list of customers
        CustomerService cust = new CustomerService();
        List<Customer> customers = cust.getAllCustomers();
        DbConnectionService.closeConnection();

        for(Customer customer : customers) {
            model.addRow(new Object[]{customer.getName(), customer.getFirstname(), customer.getLastname()
                    , customer.getEmail(), customer.getContactNo()});
        }

        // Set the Table model
        customersTable.setModel(model);


    }
}
