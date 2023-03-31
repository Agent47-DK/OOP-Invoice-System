package Components.Invoice;
import Components.Home;
import Entities.InvCust;
import Services.DbConnectionService;
import Services.InvoiceService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Invoices {
    private JPanel invoicePanel;
    private JLabel invoiceLabel;
    private JLabel homeLabel;
    private JButton createInvoiceButton;
    private JButton paymentButton;
    private JButton updateButton;
    private JButton viewButton;
    private JTable invoiceTable;

    private JFrame frame;

    public Invoices() {
        initialise();
        getInvoices();
    }

    private void initialise(){
        frame = new JFrame();
        this.frame.setTitle("Customers");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        createInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateInvoice createInvoice = new CreateInvoice();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

        this.frame.setContentPane(invoicePanel);
        this.frame.setVisible(true);
    }

    private void getInvoices() {
        // Create table model with headers
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Invoice Id");
        model.addColumn("dateCreated");
        model.addColumn("Amount Due");
        model.addColumn("Customer Name");
        model.addColumn("Email");

        InvoiceService invoiceService = new InvoiceService();
        List<InvCust> invoices = invoiceService.getInvoices();
        DbConnectionService.closeConnection();

        for(InvCust invoice: invoices) {
            model.addRow(new Object[]{invoice.getInvoiceId(), invoice.getDateCreated(), invoice.getAmountDue(),
             invoice.getName(), invoice.getEmail()});
        }
        invoiceTable.setModel(model);
    }
}
