/**
 *      Author: Douglas T Kadzutu
 *      Date: 31/03/2023
 *      Description: Invoice System used to create and manage invoices for customers
 */
import Components.Home;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Home home = new Home();
            }
        });
    }
}