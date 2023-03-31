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

public class AddProduct {
    private JPanel AddProductPanel;
    private JLabel AddProductTitle;
    private JLabel homeLabel;
    private JPanel addCustomerFormPanel;
    private JLabel productNamelabel;
    private JTextField productNameTxtField;
    private JLabel descriptionLabel;
    private JLabel priceLabel;
    private JTextField priceTxtField;
    private JLabel categoryLabel;
    private JButton AddProductBtn;
    private JButton cancelButton;
    private JComboBox categoryComboBox;
    private JTextField descriptionTxtField;

    private JFrame frame;

    public AddProduct() {
        initialise();
    }

    private void initialise(){
        frame = new JFrame();
        this.frame.setTitle("Add New Product");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        AddProductBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
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

        this.frame.setContentPane(AddProductPanel);
        //this.frame.pack(); // will be used when we want to resize
        this.frame.setVisible(true);
    }

    public void addProduct() {
        String name = productNameTxtField.getText();
        String description = descriptionTxtField.getText();
        String price = priceTxtField.getText();
        String selectedCategory = (String) categoryComboBox.getSelectedItem();

        try{
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


            // Call service and get added product back
            ProductService productService = new ProductService();
            Product product = productService.addProduct(name, description, Double.parseDouble(price), selectedCategory);
            DbConnectionService.closeConnection();

            if(product.getName() != null) {
                JOptionPane.showMessageDialog(frame,
                        "Product " + product.getName() + " has been successfully added",
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
        productNameTxtField.setText("");
        descriptionTxtField.setText("");
        priceTxtField.setText("");
    }
}
