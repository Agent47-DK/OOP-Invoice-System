package Components;

import Components.Customer.Customers;
import Components.Invoice.CreateInvoice;
import Components.Invoice.Invoices;
import Components.Product.Products;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JPanel homePanel;
    private JLabel TitleLabel;
    private JButton customerButton;
    private JButton productsButton;
    private JButton invoiceButton;
    private JButton cancelButton;

    private JFrame frame;

    public Home() {
        initialise();
    }

    private void initialise () {
        frame = new JFrame();
        this.frame.setTitle("Add New Customer");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customers customers = new Customers();
                frame.dispose();
            }
        });

        productsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Products products = new Products();
                frame.dispose();
            }
        });

        invoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Invoices invoices = new Invoices();
                frame.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        this.frame.setContentPane(homePanel);
        //this.frame.pack(); // will be used when we want to resize
        this.frame.setVisible(true);
    }
}
