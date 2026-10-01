package com.hospital.ui;

import com.hospital.dao.AdmissionDAO;
import com.hospital.dao.BillDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.models.Admission;
import com.hospital.models.Bill;
import com.hospital.models.Patient;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BillPage extends JFrame {
    
    private JButton viewButton;
    private JButton searchButton;
    private JTextField searchField;
    private JButton backButton;
    private JLabel messageLabel;
    private JTable billTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    public BillPage() {
        setTitle("Hospital Management System - View Bills");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1050, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        JLabel titleLabel = new JLabel("BILLING INFORMATION");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        
        // Control panel for buttons and search
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(new Color(240, 240, 240));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Search & Filter"));
        
        // Search field
        JLabel searchLabel = new JLabel("Search Bill ID:");
        searchField = new JTextField(10);
        searchField.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Search button
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 11));
        searchButton.setBackground(new Color(100, 150, 220));
        searchButton.setForeground(Color.WHITE);
        searchButton.addActionListener(e -> handleSearchBill());
        
        // View all button
        viewButton = new JButton("View All Bills");
        viewButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewButton.setBackground(new Color(50, 120, 200));
        viewButton.setForeground(Color.WHITE);
        viewButton.addActionListener(e -> handleViewAllBills());
        
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
        
        // Table for displaying bills
        String[] columns = {"Bill ID", "Admission ID", "Patient ID", "Patient Name", "Room Charge (₹)", "Doctor Charge (₹)", "Total Charge (₹)", "Bill Date"};
        tableModel = new DefaultTableModel(columns, 0);
        tableModel.setColumnIdentifiers(columns);
        billTable = new JTable(tableModel);
        billTable.setFont(new Font("Arial", Font.PLAIN, 10));
        billTable.setRowHeight(25);
        billTable.getTableHeader().setBackground(new Color(50, 120, 200));
        billTable.getTableHeader().setForeground(Color.WHITE);
        billTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        scrollPane = new JScrollPane(billTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Bills List"));
        
        // Add panels to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.BEFORE_FIRST_LINE);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Load all bills on startup
        handleViewAllBills();
    }
    
    /**
     * Handle viewing all bills
     */
    private void handleViewAllBills() {
        try {
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get all bills from database
            List<Bill> bills = BillDAO.getAllBills();
            
            // Add rows to table
            for (Bill bill : bills) {
                // Get admission and patient info
                Admission admission = AdmissionDAO.getAdmissionById(bill.getAdmissionId());
                String patientName = "Unknown";
                int patientId = -1;
                
                if (admission != null) {
                    patientId = admission.getPatientId();
                    Patient patient = PatientDAO.getPatientById(patientId);
                    if (patient != null) {
                        patientName = patient.getName();
                    }
                }
                
                Object[] row = {
                    bill.getId(),
                    bill.getAdmissionId(),
                    patientId,
                    patientName,
                    String.format("%.2f", bill.getRoomCharge()),
                    String.format("%.2f", bill.getDoctorCharge()),
                    String.format("%.2f", bill.getTotalCharge()),
                    bill.getBillDate()
                };
                tableModel.addRow(row);
            }
            
            messageLabel.setText("Total Bills: " + bills.size());
            messageLabel.setForeground(new Color(0, 150, 0));
            
        } catch (Exception e) {
            messageLabel.setText("Error loading bills!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle searching for a bill by ID
     */
    private void handleSearchBill() {
        try {
            String searchText = searchField.getText().trim();
            
            if (searchText.isEmpty()) {
                messageLabel.setText("Please enter a Bill ID!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Parse bill ID
            int billId;
            try {
                billId = Integer.parseInt(searchText);
            } catch (NumberFormatException e) {
                messageLabel.setText("Please enter a valid Bill ID!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get bill by ID
            Bill bill = BillDAO.getBillById(billId);
            
            if (bill != null) {
                // Get admission and patient info
                Admission admission = AdmissionDAO.getAdmissionById(bill.getAdmissionId());
                String patientName = "Unknown";
                int patientId = -1;
                
                if (admission != null) {
                    patientId = admission.getPatientId();
                    Patient patient = PatientDAO.getPatientById(patientId);
                    if (patient != null) {
                        patientName = patient.getName();
                    }
                }
                
                Object[] row = {
                    bill.getId(),
                    bill.getAdmissionId(),
                    patientId,
                    patientName,
                    String.format("%.2f", bill.getRoomCharge()),
                    String.format("%.2f", bill.getDoctorCharge()),
                    String.format("%.2f", bill.getTotalCharge()),
                    bill.getBillDate()
                };
                tableModel.addRow(row);
                messageLabel.setText("Bill found!");
                messageLabel.setForeground(new Color(0, 150, 0));
            } else {
                messageLabel.setText("Bill with ID " + billId + " not found!");
                messageLabel.setForeground(Color.RED);
            }
            
        } catch (Exception e) {
            messageLabel.setText("Error searching bill!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
}