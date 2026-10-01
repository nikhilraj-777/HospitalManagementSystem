package com.hospital.ui;


import com.hospital.dao.DoctorDAO;
import com.hospital.models.Doctor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DoctorPage extends JFrame {
    
    private JTextField nameField;
    private JTextField specializationField;
    private JTextField phoneField;
    private JButton addButton;
    private JButton viewButton;
    private JButton backButton;
    private JLabel messageLabel;
    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    public DoctorPage() {
        setTitle("Hospital Management System - Doctor Management");
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
        JLabel titleLabel = new JLabel("DOCTOR MANAGEMENT");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        
        // Form panel for adding doctors
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Doctor"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Doctor Name
        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(nameLabel, gbc);
        
        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(nameField, gbc);
        
        // Specialization
        JLabel specializationLabel = new JLabel("Specialization:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(specializationLabel, gbc);
        
        specializationField = new JTextField(20);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(specializationField, gbc);
        
        // Phone
        JLabel phoneLabel = new JLabel("Phone:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        formPanel.add(phoneLabel, gbc);
        
        phoneField = new JTextField(20);
        gbc.gridx = 1;
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
        
        addButton = new JButton("Add Doctor");
        addButton.setFont(new Font("Arial", Font.BOLD, 11));
        addButton.setBackground(new Color(50, 150, 50));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> handleAddDoctor());
        
        viewButton = new JButton("View All Doctors");
        viewButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewButton.setBackground(new Color(50, 120, 200));
        viewButton.setForeground(Color.WHITE);
        viewButton.addActionListener(e -> handleViewDoctors());
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 11));
        backButton.setBackground(new Color(200, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> this.dispose());
        
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(backButton);
        
        // Table for displaying doctors
        String[] columns = {"ID", "Name", "Specialization", "Phone"};
        tableModel = new DefaultTableModel(columns, 0);
        doctorTable = new JTable(tableModel);
        doctorTable.setFont(new Font("Arial", Font.PLAIN, 11));
        doctorTable.setRowHeight(25);
        doctorTable.getTableHeader().setBackground(new Color(50, 120, 200));
        doctorTable.getTableHeader().setForeground(Color.WHITE);
        
        scrollPane = new JScrollPane(doctorTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Doctors List"));
        
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
     * Handle adding a new doctor
     */
    private void handleAddDoctor() {
        try {
            // Validate input
            String name = nameField.getText().trim();
            String specialization = specializationField.getText().trim();
            String phone = phoneField.getText().trim();
            
            if (name.isEmpty() || specialization.isEmpty() || phone.isEmpty()) {
                messageLabel.setText("Please fill all fields!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Validate phone
            if (phone.length() < 10) {
                messageLabel.setText("Phone number must be at least 10 digits!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Create doctor object
            Doctor doctor = new Doctor(name, specialization, phone);
            
            // Add to database
            if (DoctorDAO.addDoctor(doctor)) {
                messageLabel.setText("Doctor added successfully!");
                messageLabel.setForeground(new Color(0, 150, 0));
                
                // Clear fields
                nameField.setText("");
                specializationField.setText("");
                phoneField.setText("");
                
                // Refresh table
                handleViewDoctors();
            } else {
                messageLabel.setText("Failed to add doctor! Try again.");
                messageLabel.setForeground(Color.RED);
            }
            
        } catch (Exception e) {
            messageLabel.setText("An error occurred!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle viewing all doctors
     */
    private void handleViewDoctors() {
        try {
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get all doctors from database
            List<Doctor> doctors = DoctorDAO.getAllDoctors();
            
            // Add rows to table
            for (Doctor doctor : doctors) {
                Object[] row = {
                    doctor.getId(),
                    doctor.getName(),
                    doctor.getSpecialization(),
                    doctor.getPhone()
                };
                tableModel.addRow(row);
            }
            
            messageLabel.setText("Loaded " + doctors.size() + " doctors!");
            messageLabel.setForeground(new Color(0, 150, 0));
            
        } catch (Exception e) {
            messageLabel.setText("Error loading doctors!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
}