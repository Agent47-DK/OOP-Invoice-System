package Components.Product;

import Components.Customer.DeleteCustomer;
import Entities.Product;
import Helper.Utils;
import Services.CustomerService;
import Services.DbConnectionService;
import Services.ProductService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DeleteProduct {
    private JPanel DeleteProductPanel;
    private JLabel DeleteProductTitle;
    private JLabel homeLabel;
    private JPanel addCustomerFormPanel;
    private JLabel productNameLabel;
    private JTextField productNameTxtField;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JTextField priceTxtField;
    private JLabel categoryLabel;
    private JButton deleteButton;
    private JTextField descriptionTxtField;
    private JTextField searchTextField;
    private JLabel searchLabel;
    private JTextField categoryTxtField;

    private JFrame frame;

    public DeleteProduct() {
        initialise();
    }

    private void initialise() {
        frame = new JFrame();
        this.frame.setTitle("Delete Product");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
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

        this.frame.setContentPane(DeleteProductPanel);
        //this.frame.pack(); // will be used when we want to resize
        this.frame.setVisible(true);
    }

    private void deleteProduct() {
        String productName = productNameTxtField.getText();
        if(productName.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Please Select Product",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int option = JOptionPane.showConfirmDialog(frame,
                "You are about to delete " + productName,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if(option == JOptionPane.OK_OPTION) {
            ProductService productService = new ProductService();
            boolean deleted = productService.deleteProduct(productName);
            DbConnectionService.closeConnection();

            if(deleted) {
                JOptionPane.showMessageDialog(frame,
                        "Product " + productName + " has been successfully deleted",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
                frame.dispose();
                DeleteProduct deleteProduct = new DeleteProduct();
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Error Deleting Product ",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    private void findProduct() {
        String productName = searchTextField.getText();

        if (productName.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Please Enter Product Name",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!Utils.validateName(productName)) {
            JOptionPane.showMessageDialog(frame,
                    "Product Name cannot be a number",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        ProductService productService = new ProductService();
        Product product = productService.getProductByName(productName);
        DbConnectionService.closeConnection();
        if(product.getName() != null) {
            JOptionPane.showMessageDialog(frame,
                    "Product " + product.getName() + " has been successfully retrieved",
                    "Success",
                    JOptionPane.PLAIN_MESSAGE);
            fillDetails(product);
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Product could not be retrieved",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // helper Methods
    private void fillDetails(Product product) {
        productNameTxtField.setText(product.getName());
        descriptionTxtField.setText(product.getDescription());
        priceTxtField.setText(String.valueOf(product.getPrice()));
        categoryTxtField.setText(String.valueOf(product.getCategory()));
    }
}
