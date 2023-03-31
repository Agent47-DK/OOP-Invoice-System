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

public class DeleteCustomer {
    private JPanel DeleteCustomerPanel;
    private JLabel DeleteCustomerTitle;
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
    private JButton deleteButton;
    private JTextField searchTxtField;
    private JLabel searchLabel;

    private JFrame frame;

    public DeleteCustomer() {
        initialise();
    }

    private void initialise(){
        frame = new JFrame();
        this.frame.setTitle("Delete Customer");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        searchTxtField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findCustomer();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
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


        this.frame.setContentPane(DeleteCustomerPanel);
        //this.frame.pack(); // will be used when we want to resize
        this.frame.setVisible(true);
    }

    private void deleteCustomer() {
        String companyName = companyNameTxtField.getText();
        if(companyName.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Please Select Customer",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int option = JOptionPane.showConfirmDialog(frame,
                "You are about to delete " + companyName,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if(option == JOptionPane.OK_OPTION) {
            CustomerService customerService = new CustomerService();
            boolean deleted = customerService.deleteCustomer(companyName);
            DbConnectionService.closeConnection();

            if(deleted) {
                JOptionPane.showMessageDialog(frame,
                        "Customer " + companyName + " has been successfully deleted",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
                frame.dispose();
                DeleteCustomer deleteCustomer = new DeleteCustomer();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Error Deleting Customer ",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
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
        }
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
    }
}
