package Components.Customer;

import Entities.Customer;
import Helper.Utils;
import Services.CustomerService;
import Services.DbConnectionService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class AddCustomer {
    private JPanel AddCustomerPanel;
    private JLabel AddCustomerTitle;
    private JLabel homeLabel;
    private JPanel addCustomerFormPanel;
    private JLabel companyNamelabel;
    private JTextField companyNameTxtField;
    private JLabel firstNameLabel;
    private JTextField firstNameTxtField;
    private JLabel lastNamelabel;
    private JTextField lastnameTxtField;
    private JLabel CustomerEmailTxtField;
    private JTextField customerEmailTxtField;
    private JLabel customerContactLabel;
    private JTextField customerContactTxtField;
    private JLabel customerWebsiteLabel;
    private JTextField customerWebsiteTxtField;
    private JLabel CustomerAddresslabel;
    private JTextArea addressTxtArea;
    private JButton AddCustomerBtn;
    private JButton cancelButton;

    private JFrame frame;

    public AddCustomer() {
        initialise();
    }

    private void initialise() {
        frame = new JFrame();
        this.frame.setTitle("Add New Customer");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        AddCustomerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAdd();
            }
        });

        homeLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

        this.frame.setContentPane(AddCustomerPanel);
        //this.frame.pack(); // will be used when we want to resize
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
                        "Customer " + customer.getName() + " has been successfully added",
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
}
