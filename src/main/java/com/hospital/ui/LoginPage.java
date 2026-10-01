package com.hospital.ui;

import com.hospital.dao.UserDAO;
import com.hospital.models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginPage extends JFrame {
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton exitButton;
    private JLabel messageLabel;
    
    // Constructor
    public LoginPage() {
        // Set frame properties
        setTitle("Hospital Management System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);  // Center the window
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Create header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        JLabel titleLabel = new JLabel("HOSPITAL MANAGEMENT SYSTEM");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        
        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(usernameLabel, gbc);
        
        // Username field
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        formPanel.add(usernameField, gbc);
        
        // Password label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(passwordLabel, gbc);
        
        // Password field
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.7;
        formPanel.add(passwordField, gbc);
        
        // Message label (for success/error messages)
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        formPanel.add(messageLabel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        // Login button
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 12));
        loginButton.setBackground(new Color(50, 120, 200));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.addActionListener(new LoginButtonListener());
        
        // Exit button
        exitButton = new JButton("EXIT");
        exitButton.setFont(new Font("Arial", Font.BOLD, 12));
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setForeground(Color.WHITE);
        exitButton.setPreferredSize(new Dimension(100, 35));
        exitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Handle close button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    
    /**
     * Action listener for login button
     */
    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            handleLogin();
        }
    }
    
    /**
     * Handle login logic
     */
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please enter username and password!");
            messageLabel.setForeground(Color.RED);
            return;
        }
        
        // Try to login using UserDAO
        User user = UserDAO.loginUser(username, password);
        
        if (user != null) {
            // Login successful
            messageLabel.setText("Login successful! Redirecting...");
            messageLabel.setForeground(new Color(0, 150, 0));
            
            // Wait 1 second, then open dashboard
            Timer timer = new Timer(1000, e -> openDashboard(user));
            timer.setRepeats(false);
            timer.start();
        } else {
            // Login failed
            messageLabel.setText("Invalid username or password!");
            messageLabel.setForeground(Color.RED);
            passwordField.setText("");  // Clear password field
        }
    }
    
    /**
     * Open appropriate dashboard based on user role
     */
    private void openDashboard(User user) {
        this.dispose();  // Close login window
        
        if ("ADMIN".equals(user.getRole())) {
            // Open Admin Dashboard
            AdminDashboard adminDash = new AdminDashboard(user);
            adminDash.setVisible(true);
        } else if ("STAFF".equals(user.getRole())) {
            // Open Staff Dashboard
            StaffDashboard staffDash = new StaffDashboard(user);
            staffDash.setVisible(true);
        }
    }
    
    /**
     * Main method to run the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}