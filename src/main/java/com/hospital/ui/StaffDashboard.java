package com.hospital.ui;

import com.hospital.models.User;
import javax.swing.*;
import java.awt.*;

public class StaffDashboard extends JFrame {
    
    private User currentUser;
    
    public StaffDashboard(User user) {
        this.currentUser = user;
        
        setTitle("Hospital Management System - Staff Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        JLabel titleLabel = new JLabel("STAFF DASHBOARD - Welcome " + currentUser.getUsername());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(titleLabel);
        
        // Content panel with buttons
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 2, 10, 10));
        contentPanel.setBackground(new Color(240, 240, 240));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Add Patient button
        JButton addPatientBtn = createButton("Add Patient");
        addPatientBtn.addActionListener(e -> openPatientPage());
        
        // View Patients button
        JButton viewPatientsBtn = createButton("View Patients");
        viewPatientsBtn.addActionListener(e -> openPatientPage());
        
        // Admit Patient button
        JButton admitPatientBtn = createButton("Admit Patient");
        admitPatientBtn.addActionListener(e -> openAdmissionPage());
        
        // Discharge / Bill button
        // Discharge / Bill button
        JButton dischargeBtn = createButton("Discharge / Bill");
        dischargeBtn.addActionListener(e -> openDischargeBillingPage());
        
        contentPanel.add(addPatientBtn);
        contentPanel.add(viewPatientsBtn);
        contentPanel.add(admitPatientBtn);
        contentPanel.add(dischargeBtn);
        
        
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
     * Open the Patient Management page
     */
    private void openPatientPage() {
        PatientPage patientPage = new PatientPage();
        patientPage.setVisible(true);
    }
    
    /**
     * Open the Admission page
     */
    private void openAdmissionPage() {
        AdmissionPage admissionPage = new AdmissionPage();
        admissionPage.setVisible(true);
    }
    /**
 * Open the Discharge & Billing page
 */
    private void openDischargeBillingPage() {
        DischargeBillingPage dischargePage = new DischargeBillingPage();
        dischargePage.setVisible(true);
    }
    
    private void logout() {
        this.dispose();
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }
}