package com.hospital.ui;

import com.hospital.dao.PatientDAO;
import com.hospital.models.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPatientPage extends JFrame {
    
    private JButton viewButton;
    private JButton searchButton;
    private JTextField searchField;
    private JButton backButton;
    private JLabel messageLabel;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    public AdminPatientPage() {
        setTitle("Hospital Management System - View Patients (Admin)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        JLabel titleLabel = new JLabel("VIEW PATIENTS (ADMIN)");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        
        // Control panel for buttons and search
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(new Color(240, 240, 240));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Search & Filter"));
        
        // Search field
        JLabel searchLabel = new JLabel("Search Patient ID:");
        searchField = new JTextField(10);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Search button
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 11));
        searchButton.setBackground(new Color(100, 150, 220));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> handleSearchPatient());
        
        // View all button
        viewButton = new JButton("View All Patients");
        viewButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewButton.setBackground(new Color(50, 120, 200));
        viewButton.setForeground(Color.WHITE);
        viewButton.addActionListener(e -> handleViewAllPatients());
        
        // Back button
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 11));
        backButton.setBackground(new Color(200, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> this.dispose());
        
        // Message label
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        messageLabel.setForeground(Color.RED);
        
        controlPanel.add(searchLabel);
        controlPanel.add(searchField);
        controlPanel.add(searchButton);
        controlPanel.add(viewButton);
        controlPanel.add(backButton);
        controlPanel.add(messageLabel);
        
        // Table for displaying patients
        String[] columns = {"ID", "Name", "Age", "Gender", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        tableModel.setColumnIdentifiers(columns);
        patientTable = new JTable(tableModel);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 11));
        patientTable.setRowHeight(25);
        patientTable.getTableHeader().setBackground(new Color(50, 120, 200));
        patientTable.getTableHeader().setForeground(Color.WHITE);
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(patientTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Patients List"));
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Load all patients on startup
        handleViewAllPatients();
    }
    
    /**
     * Handle viewing all patients
     */
    private void handleViewAllPatients() {
        try {
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get all patients from database
            List<Patient> patients = PatientDAO.getAllPatients();
            
            // Add rows to table
            for (Patient patient : patients) {
                Object[] row = {
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getPhone()
                };
                tableModel.addRow(row);
            }
            
            messageLabel.setText("Total Patients: " + patients.size());
            messageLabel.setForeground(new Color(0, 150, 0));
            
        } catch (Exception e) {
            messageLabel.setText("Error loading patients!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle searching for a patient by ID
     */
    private void handleSearchPatient() {
        try {
            String searchText = searchField.getText().trim();
            
            if (searchText.isEmpty()) {
                messageLabel.setText("Please enter a Patient ID!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Parse patient ID
            int patientId;
            try {
                patientId = Integer.parseInt(searchText);
            } catch (NumberFormatException e) {
                messageLabel.setText("Please enter a valid Patient ID!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get patient by ID
            Patient patient = PatientDAO.getPatientById(patientId);
            
            if (patient != null) {
                Object[] row = {
                    patient.getId(),
                    patient.getName(),
                    patient.getAge(),
                    patient.getGender(),
                    patient.getPhone()
                };
                tableModel.addRow(row);
                messageLabel.setText("Patient found!");
                messageLabel.setForeground(new Color(0, 150, 0));
            } else {
                messageLabel.setText("Patient with ID " + patientId + " not found!");
                messageLabel.setForeground(Color.RED);
            }
            
        } catch (Exception e) {
            messageLabel.setText("Error searching patient!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
}