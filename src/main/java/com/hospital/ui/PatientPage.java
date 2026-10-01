package com.hospital.ui;

import com.hospital.dao.PatientDAO;
import com.hospital.models.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PatientPage extends JFrame {
    
    private JTextField nameField;
    private JTextField ageField;
    private JComboBox<String> genderCombo;
    private JTextField phoneField;
    private JButton addButton;
    private JButton viewButton;
    private JButton backButton;
    private JLabel messageLabel;
    private JTable patientTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    public PatientPage() {
        setTitle("Hospital Management System - Patient Management");
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
        JLabel titleLabel = new JLabel("PATIENT MANAGEMENT");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        
        // Form panel for adding patients
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Patient"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Patient Name
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(nameLabel, gbc);
        
        nameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(nameField, gbc);
        
        // Age
        JLabel ageLabel = new JLabel("Age:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(ageLabel, gbc);
        
        ageField = new JTextField(5);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(ageField, gbc);
        
        // Gender
        JLabel genderLabel = new JLabel("Gender:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        formPanel.add(genderLabel, gbc);
        
        genderCombo = new JComboBox<>(new String[]{"MALE", "FEMALE", "OTHER"});
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(genderCombo, gbc);
        
        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        formPanel.add(phoneLabel, gbc);
        
        phoneField = new JTextField(15);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(phoneField, gbc);
        
        // Message label
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        formPanel.add(messageLabel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        addButton = new JButton("Add Patient");
        addButton.setFont(new Font("Arial", Font.BOLD, 11));
        addButton.setBackground(new Color(50, 150, 50));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> handleAddPatient());
        
        viewButton = new JButton("View All Patients");
        viewButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewButton.setBackground(new Color(50, 120, 200));
        viewButton.setForeground(Color.WHITE);
        viewButton.addActionListener(e -> handleViewPatients());
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 11));
        backButton.setBackground(new Color(200, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> this.dispose());
        
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(backButton);
        
        // Table for displaying patients
        String[] columns = {"ID", "Name", "Age", "Gender", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        patientTable = new JTable(tableModel);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 11));
        patientTable.setRowHeight(25);
        patientTable.getTableHeader().setBackground(new Color(50, 120, 200));
        patientTable.getTableHeader().setForeground(Color.WHITE);
        
        scrollPane = new JScrollPane(patientTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Patients List"));
        
        // Add all panels to main panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(formPanel);
        topPanel.add(buttonPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.PAGE_START);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    /**
     * Handle adding a new patient
     */
    private void handleAddPatient() {
        try {
            // Validate input
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String gender = (String) genderCombo.getSelectedItem();
            String phone = phoneField.getText().trim();
            
            if (name.isEmpty() || ageText.isEmpty() || phone.isEmpty()) {
                messageLabel.setText("Please fill all fields!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Parse age
            int age;
            try {
                age = Integer.parseInt(ageText);
                if (age < 0 || age > 150) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                messageLabel.setText("Please enter a valid age (0-150)!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Validate phone
            if (phone.length() < 10) {
                messageLabel.setText("Phone number must be at least 10 digits!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Create patient object
            Patient patient = new Patient(name, age, gender, phone);
            
            // Add to database
            if (PatientDAO.addPatient(patient)) {
                messageLabel.setText("Patient added successfully!");
                messageLabel.setForeground(new Color(0, 150, 0));
                
                // Clear fields
                nameField.setText("");
                ageField.setText("");
                phoneField.setText("");
                genderCombo.setSelectedIndex(0);
                
                // Refresh table
                handleViewPatients();
            } else {
                messageLabel.setText("Failed to add patient! Try again.");
                messageLabel.setForeground(Color.RED);
            }
            
        } catch (Exception e) {
            messageLabel.setText("An error occurred!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle viewing all patients
     */
    private void handleViewPatients() {
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
            
            messageLabel.setText("Loaded " + patients.size() + " patients!");
            messageLabel.setForeground(new Color(0, 150, 0));
            
        } catch (Exception e) {
            messageLabel.setText("Error loading patients!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
}