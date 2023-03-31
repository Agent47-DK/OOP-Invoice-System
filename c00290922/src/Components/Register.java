package Components;
/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class handles the User inputs for registration and calls the registration service
 *      once all fields have been checked
 */
import Entities.User;
import Helper.Utils;
import Services.DbConnectionService;
import Services.RegisterService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Register {
    private JPanel RegisterMainPanel;
    private JTextField usernameTxtField;
    private JTextField emailTxtField;
    private JTextField contactNoTxtField;
    private JComboBox<String> roleComboBox;
    private JPasswordField passwordField;
    private JPasswordField confPasswordField;
    private JButton registerBtn;
    private JButton cancelButton;
    private JLabel RegisterLabel;
    private JLabel usernameLabel;
    private JLabel emailLabel;
    private JLabel contactLabel;
    private JLabel roleLabel;
    private JLabel passwordLabel;
    private JLabel confPasswordLabel;
    private JLabel loginLabel;

    private JFrame frame;


    public Register() {
        initialise();
    }

    private void initialise(){
        frame = new JFrame();
        this.frame.setTitle("Register User");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
            }
        });

        loginLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Login login = new Login();
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

        this.roleComboBox.addItem("Admin");
        this.roleComboBox.addItem("User");


        this.frame.setContentPane(RegisterMainPanel);
        this.frame.setVisible(true);
    }

    public void registerUser() {
        String username = usernameTxtField.getText();
        String email = emailTxtField.getText();
        String contactNo = contactNoTxtField.getText();
        String password = String.valueOf(passwordField.getPassword());
        String confPassword = String.valueOf(confPasswordField.getPassword());
        String selectedRole = (String) roleComboBox.getSelectedItem();

        try {
            // validations
            if (username.isEmpty() || email.isEmpty() || contactNo.isEmpty() || password.isEmpty() ||
                    confPassword.isEmpty()) {
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

            if (!password.equals(confPassword)){
                JOptionPane.showMessageDialog(frame,
                        "Passwords Do Not Match",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try{
                RegisterService registerService = new RegisterService();
                User user = registerService.addUser(username, password, email, contactNo, selectedRole);
                DbConnectionService.closeConnection();

                if(user.getUsername() != null) {
                    JOptionPane.showMessageDialog(frame,
                            "User " + user.getUsername() + "has been successfully added, Please Login",
                            "Success",
                            JOptionPane.PLAIN_MESSAGE);
                    Login login = new Login();
                    frame.dispose();
                }

            } catch (Exception e) {
                // Handle any other exceptions that might occur
                JOptionPane.showMessageDialog(frame,
                        "An unexpected error occurred: \n" + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }


        } catch(Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "An unexpected error occurred: \n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }


    }

    public void cancel(){
        // Clear input fields
        usernameTxtField.setText("");
        emailTxtField.setText("");
        usernameTxtField.setText("");
        contactNoTxtField.setText("");
        passwordField.setText(String.valueOf(new char[0]));
        confPasswordField.setText(String.valueOf(new char[0]));

    }

}
