package Components;
/**
 *      Author: Douglas T Kadzutu
 *      Date: 22/03/2023
 *      Description: This class is the login UI that sends data to the login service
 */

import Services.DbConnectionService;
import Services.LoginService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Login {
    private JPanel LoginMainPanel;
    private JPanel LoginFormPanel;
    private JTextField usernameTxtBox;
    private JPasswordField passwordTxtBox;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JButton loginBtn;
    private JButton cancelButton;
    private JLabel titleLabel;
    private JLabel forgotPasswordLabel;
    private JLabel signUpLabel;


    private JFrame frame;

    // Set Panel Properties
    public Login() {
        initialise();
   }

    private void initialise() {
        frame = new JFrame();
        this.frame.setTitle("Enter Login Details");
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancel();
                   }
        });

        forgotPasswordLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame,
                        "Redirect to forgot password page",
                        "forgot password",
                        JOptionPane.PLAIN_MESSAGE);
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

        signUpLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Register register = new Register();
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
        this.frame.setContentPane(LoginMainPanel);
        //this.frame.pack(); // will be used when we want to resize
        this.frame.setVisible(true);
    }
    public void loginUser() {
        String username = usernameTxtBox.getText();
        String password = String.valueOf(passwordTxtBox.getPassword());

        try {
            LoginService loginService = new LoginService();
            boolean authenticated = loginService.authenticate(username, password);
            DbConnectionService.closeConnection();


            if (authenticated) {
                JOptionPane.showMessageDialog(frame,
                        "Login Successful",
                        "Success",
                        JOptionPane.PLAIN_MESSAGE);
                Dashboard dashboard = new Dashboard();
                frame.dispose();

            } else {
                JOptionPane.showMessageDialog(frame,
                        "Username or password is incorrect",
                        "Try again",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancel(){
        usernameTxtBox.setText("");
        passwordTxtBox.setText(String.valueOf(new char[0]));

    }


}
