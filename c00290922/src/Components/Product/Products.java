package Components.Product;



import Components.Home;
import Entities.Product;
import Services.DbConnectionService;
import Services.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Products {
    private JPanel ProductsPanel;
    private JTable productsTable;
    private JLabel productsLabel;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton addButton;
    private JLabel homeLabel;

    private JFrame frame;

    public Products() {
        initialise();
        getProducts();
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
                AddProduct product = new AddProduct();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateProduct product = new UpdateProduct();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteProduct product = new DeleteProduct();

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

        this.frame.setContentPane(ProductsPanel);
        this.frame.setVisible(true);
    }

    public void getProducts() {
        // Define table headers

        // Create table model with headers
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("productId");
        model.addColumn("name");
        model.addColumn("description");
        model.addColumn("price");
        model.addColumn("category");



        // Get list of products
        ProductService prod = new ProductService();
        List<Product> products = prod.getAllProducts();
        DbConnectionService.closeConnection();

        for(Product product : products) {
            model.addRow(new Object[]{product.getProductId(), product.getName(), product.getDescription()
                    , product.getPrice(), product.getCategory()});
        }

        // Set the Table model
        productsTable.setModel(model);


    }
}

