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

public class UpdateCustomer {
    private JPanel UpdateCustomerPanel;
    private JLabel UpdateCustomerTitle;
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
    private JButton updateButton;
    private JTextField searchTxtField;
    private JLabel searchLabel;

    private JFrame frame;

    private Integer customerId = null; // store customer id

    public UpdateCustomer(){
        initialise();
    }

    private void initialise() {
        frame = new JFrame();
        this.frame.setTitle("Update Customer");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        searchTxtField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findCustomer();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
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


        this.frame.setContentPane(UpdateCustomerPanel);
        this.frame.setVisible(true);
    }

    private void updateCustomer() {
        String name = companyNameTxtField.getText();
        String firstname = firstNameTxtField.getText();
        String lastname = lastnameTxtField.getText();
        String email = customerEmailTxtField.getText();
        String contactNo = customerContactTxtField.getText();
        String website = customerWebsiteTxtField.getText();
        String address = addressTxtArea.getText();

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

        if(customerId == null){
            JOptionPane.showMessageDialog(frame,
                    "Error Retrieving customer Id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


        int option = JOptionPane.showConfirmDialog(frame,
                "You are about to update " + name,
                "Confirm Update",
                JOptionPane.YES_NO_OPTION);

        if(option == JOptionPane.OK_OPTION) {
            Customer customer = new Customer();
            customer.setCustomerId(this.customerId);
            customer.setName(name);
            customer.setFirstname(firstname);
            customer.setLastname(lastname);
            customer.setEmail(email);
            customer.setContactNo(contactNo);
            customer.setWebsite(website);
            customer.setAddress(address);
            try{
                CustomerService customerService = new CustomerService();
                boolean updated = customerService.updateCustomer(customer);
                DbConnectionService.closeConnection();

                if(updated) {
                    JOptionPane.showMessageDialog(frame,
                            "Customer " + name + " has been successfully update",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                    frame.dispose();
                    UpdateCustomer updateCustomer = new UpdateCustomer();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Error Updating Customer ",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    private void findCustomer() {
        String companyName = searchTxtField.getText();

        if (companyName.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Please Enter Company Name",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Utils.validateName(companyName)) {
            JOptionPane.showMessageDialog(frame,
                    "Company Name cannot be a number",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } try {
            CustomerService customerService = new CustomerService();
            Customer customer = customerService.getCustomerByCompanyName(companyName);
            DbConnectionService.closeConnection();
            if(customer.getName() != null) {
                JOptionPane.showMessageDialog(frame,
                        "Customer " + customer.getName() + " has been successfully retrieved",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
                fillDetails(customer);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Customer could not be retrieved",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // helper Methods
    private void fillDetails(Customer customer) {
        companyNameTxtField.setText(customer.getName());
        firstNameTxtField.setText(customer.getFirstname());
        lastnameTxtField.setText(customer.getLastname());
        customerEmailTxtField.setText(customer.getEmail());
        customerContactTxtField.setText(customer.getContactNo());
        customerWebsiteTxtField.setText(customer.getWebsite());
        addressTxtArea.setText(customer.getAddress());
        this.customerId = customer.getCustomerId();

    }
}
