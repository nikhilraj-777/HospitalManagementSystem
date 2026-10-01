package com.hospital.ui;

import com.hospital.models.User;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {
    
    private User currentUser;
    
    public AdminDashboard(User user) {
        this.currentUser = user;
        
        setTitle("Hospital Management System - Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        JLabel titleLabel = new JLabel("ADMIN DASHBOARD - Welcome " + currentUser.getUsername());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(titleLabel);
        
        // Content panel with buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(3, 2, 10, 10));
        contentPanel.setBackground(new Color(240, 240, 240));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add Doctor button
        JButton addDoctorBtn = createButton("Add Doctor");
        addDoctorBtn.addActionListener(e -> openDoctorPage());
        
        // View Doctors button
        JButton viewDoctorsBtn = createButton("View Doctors");
        viewDoctorsBtn.addActionListener(e -> openDoctorPage());
        
        // Add Room button
        JButton addRoomBtn = createButton("Add Room");
        addRoomBtn.addActionListener(e -> openRoomPage());
        
        // View Rooms button
        JButton viewRoomsBtn = createButton("View Rooms");
        viewRoomsBtn.addActionListener(e -> openRoomPage());
        
        // View Patients button
        JButton viewPatientsBtn = createButton("View Patients");
        viewPatientsBtn.addActionListener(e -> openAdminPatientPage());
        
        // View Bills button
        JButton viewBillsBtn = createButton("View Bills");
        viewBillsBtn.addActionListener(e -> openBillPage());
        
        contentPanel.add(addDoctorBtn);
        contentPanel.add(viewDoctorsBtn);
        contentPanel.add(addRoomBtn);
        contentPanel.add(viewRoomsBtn);
        contentPanel.add(viewPatientsBtn);
        contentPanel.add(viewBillsBtn);
        
        // Logout button
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 240, 240));
        JButton logoutButton = new JButton("LOGOUT");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 12));
        logoutButton.setBackground(new Color(200, 50, 50));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setPreferredSize(new Dimension(120, 35));
        logoutButton.addActionListener(e -> logout());
        footerPanel.add(logoutButton);
        
        // Add panels to main panel
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        panel.add(footerPanel, BorderLayout.SOUTH);
        
        add(panel);
    }
    
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(new Color(100, 150, 220));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
    
    /**
     * Open the Doctor Management page
     */
    private void openDoctorPage() {
        DoctorPage doctorPage = new DoctorPage();
        doctorPage.setVisible(true);
    }
    
    /**
     * Open the Room Management page
     */
    private void openRoomPage() {
        RoomPage roomPage = new RoomPage();
        roomPage.setVisible(true);
    }
    
    /**
     * Open the Admin Patient View page
     */
    private void openAdminPatientPage() {
        AdminPatientPage adminPatientPage = new AdminPatientPage();
        adminPatientPage.setVisible(true);
    }
    
    /**
     * Open the Bill View page
     */
    private void openBillPage() {
        BillPage billPage = new BillPage();
        billPage.setVisible(true);
    }
    
    private void logout() {
        this.dispose();
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}