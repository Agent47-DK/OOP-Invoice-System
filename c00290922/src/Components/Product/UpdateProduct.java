package Components.Product;


import Entities.Product;
import Helper.Utils;
import Services.DbConnectionService;
import Services.ProductService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UpdateProduct {
    private JPanel UpdateProductPanel;
    private JLabel UpdateProductTitle;
    private JLabel homeLabel;
    private JPanel addCustomerFormPanel;
    private JLabel productNameLabel;
    private JTextField productNameTxtField;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JTextField priceTxtField;
    private JButton updateButton;
    private JTextField descriptionTxtField;
    private JTextField searchTextField;
    private JLabel searchLabel;
    private JTextField categoryTxtField;
    private JComboBox categoryComboBox;

    private Integer productId = null; // to store productId

    private JFrame frame;

    public UpdateProduct() {
        initialise();
    }


    private void initialise () {
        frame = new JFrame();
        this.frame.setTitle("Update Product");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        searchTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findProduct();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                updateProduct();
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

        this.frame.setContentPane(UpdateProductPanel);
        this.frame.setVisible(true);
    }

    private void updateProduct() {
        String name = productNameTxtField.getText();
        String description = descriptionTxtField.getText();
        String price = priceTxtField.getText();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();

        if (name.isEmpty() || description.isEmpty() || price.isEmpty() || selectedCategory.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Please Fill all Fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!Utils.validatePrice(price)){
            JOptionPane.showMessageDialog(frame,
                    "Please Correct price, Up to 2 decimal places and it is a number",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }



        if(productId == null){
            JOptionPane.showMessageDialog(frame,
                    "Error Retrieving Product Id",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }


        int option = JOptionPane.showConfirmDialog(frame,
                "You are about to update " + name,
                "Confirm Update",
                JOptionPane.YES_NO_OPTION);

        if(option == JOptionPane.OK_OPTION) {
            Product product = new Product();
            product.setProductId(this.productId);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(Double.parseDouble(price));
            product.setCategory(Product.Category.valueOf(selectedCategory));

            try{
                ProductService productService = new ProductService();
                boolean updated = productService.updateProduct(product);
                DbConnectionService.closeConnection();

                if(updated) {
                    JOptionPane.showMessageDialog(frame,
                            "Product " + name + " has been successfully update",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                    frame.dispose();
                    UpdateProduct updateProduct = new UpdateProduct();
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Error Updating Product ",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }catch (Exception e) {
                e.printStackTrace();
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
        if(String.valueOf(product.getCategory()).equals("sell")) {
            this.categoryComboBox.setSelectedItem(this.categoryComboBox.getItemAt(2));
        } else {
            this.categoryComboBox.setSelectedItem(this.categoryComboBox.getItemAt(1));
        }
        this.productId = product.getProductId();


    }

}
