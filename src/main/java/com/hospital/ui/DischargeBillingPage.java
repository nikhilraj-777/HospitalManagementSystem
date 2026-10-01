package com.hospital.ui;

import com.hospital.dao.AdmissionDAO;
import com.hospital.dao.BillDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.RoomDAO;
import com.hospital.models.Admission;
import com.hospital.models.Bill;
import com.hospital.models.Patient;
import com.hospital.models.Room;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class DischargeBillingPage extends JFrame {
    
    private JComboBox<String> admissionCombo;
    private JTextField dischargeDate;
    private JTextField roomChargeField;
    private JTextField doctorChargeField;
    private JTextField totalChargeField;
    private JButton calculateButton;
    private JButton dischargeButton;
    private JButton viewButton;
    private JButton backButton;
    private JLabel messageLabel;
    private JTable billingTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    // Store admission objects for lookup
    private List<Admission> activeAdmissions;
    
    public DischargeBillingPage() {
        setTitle("Hospital Management System - Discharge & Billing");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        JLabel titleLabel = new JLabel("PATIENT DISCHARGE & BILLING");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        
        // Form panel for discharge and billing
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder("Discharge Patient & Generate Bill"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Admission selection
        JLabel admissionLabel = new JLabel("Select Admission:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(admissionLabel, gbc);
        
        admissionCombo = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        formPanel.add(admissionCombo, gbc);
        
        JButton loadAdmissionBtn = new JButton("Load Details");
        loadAdmissionBtn.setFont(new Font("Arial", Font.BOLD, 10));
        loadAdmissionBtn.setBackground(new Color(100, 150, 220));
        loadAdmissionBtn.setForeground(Color.WHITE);
        loadAdmissionBtn.addActionListener(e -> handleLoadAdmissionDetails());
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        formPanel.add(loadAdmissionBtn, gbc);
        
        // Discharge date
        JLabel dischargeDateLabel = new JLabel("Discharge Date:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        formPanel.add(dischargeDateLabel, gbc);
        
        dischargeDate = new JTextField(10);
        dischargeDate.setText(LocalDate.now().toString());  // Default to today
        dischargeDate.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        formPanel.add(dischargeDate, gbc);
        
        JLabel dateFormatLabel = new JLabel("(YYYY-MM-DD)");
        dateFormatLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.15;
        formPanel.add(dateFormatLabel, gbc);
        
        // Room charge
        JLabel roomChargeLabel = new JLabel("Room Charge (₹):");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.2;
        formPanel.add(roomChargeLabel, gbc);
        
        roomChargeField = new JTextField(10);
        roomChargeField.setEditable(false);
        roomChargeField.setBackground(new Color(220, 220, 220));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        formPanel.add(roomChargeField, gbc);
        
        // Doctor charge
        JLabel doctorChargeLabel = new JLabel("Doctor Charge (₹):");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.2;
        formPanel.add(doctorChargeLabel, gbc);
        
        doctorChargeField = new JTextField(10);
        doctorChargeField.setText("500");  // Default doctor charge
        doctorChargeField.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        formPanel.add(doctorChargeField, gbc);
        
        // Calculate button
        calculateButton = new JButton("Calculate Bill");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 10));
        calculateButton.setBackground(new Color(50, 120, 200));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.addActionListener(e -> handleCalculateBill());
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 0.15;
        formPanel.add(calculateButton, gbc);
        
        // Total charge
        JLabel totalChargeLabel = new JLabel("Total Charge (₹):");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        formPanel.add(totalChargeLabel, gbc);
        
        totalChargeField = new JTextField(10);
        totalChargeField.setEditable(false);
        totalChargeField.setBackground(new Color(220, 220, 220));
        totalChargeField.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        formPanel.add(totalChargeField, gbc);
        
        // Message label
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        formPanel.add(messageLabel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        dischargeButton = new JButton("Discharge Patient & Generate Bill");
        dischargeButton.setFont(new Font("Arial", Font.BOLD, 11));
        dischargeButton.setBackground(new Color(50, 150, 50));
        dischargeButton.setForeground(Color.WHITE);
        dischargeButton.addActionListener(e -> handleDischargePatient());
        
        viewButton = new JButton("View All Bills");
        viewButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewButton.setBackground(new Color(50, 120, 200));
        viewButton.setForeground(Color.WHITE);
        viewButton.addActionListener(e -> handleViewBills());
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 11));
        backButton.setBackground(new Color(200, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> this.dispose());
        
        buttonPanel.add(dischargeButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(backButton);
        
        // Table for displaying billing history
        String[] columns = {"Bill ID", "Admission ID", "Patient Name", "Room Charge (₹)", "Doctor Charge (₹)", "Total Charge (₹)", "Bill Date"};
        tableModel = new DefaultTableModel(columns, 0);
        billingTable = new JTable(tableModel);
        billingTable.setFont(new Font("Arial", Font.PLAIN, 10));
        billingTable.setRowHeight(25);
        billingTable.getTableHeader().setBackground(new Color(50, 120, 200));
        billingTable.getTableHeader().setForeground(Color.WHITE);
        
        scrollPane = new JScrollPane(billingTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Billing History"));
        
        // Add all panels to main panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.add(formPanel);
        topPanel.add(buttonPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(topPanel, BorderLayout.PAGE_START);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        
        // Load active admissions on startup
        loadActiveAdmissions();
    }
    
    /**
     * Load all active admissions into combo box
     */
    private void loadActiveAdmissions() {
        try {
            admissionCombo.removeAllItems();
            activeAdmissions = AdmissionDAO.getActiveAdmissions();
            
            if (activeAdmissions.isEmpty()) {
                admissionCombo.addItem("No active admissions");
                messageLabel.setText("No patients currently admitted!");
                messageLabel.setForeground(Color.RED);
            } else {
                for (Admission admission : activeAdmissions) {
                    Patient patient = PatientDAO.getPatientById(admission.getPatientId());
                    String patientName = (patient != null) ? patient.getName() : "Unknown";
                    
                    admissionCombo.addItem("Admission ID: " + admission.getId() + " - Patient: " + patientName + 
                        " (Admitted: " + admission.getAdmissionDate() + ")");
                }
                messageLabel.setText("Select an admission to discharge");
                messageLabel.setForeground(new Color(0, 100, 150));
            }
        } catch (Exception e) {
            messageLabel.setText("Error loading admissions!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle loading admission details
     */
    private void handleLoadAdmissionDetails() {
        try {
            int selectedIndex = admissionCombo.getSelectedIndex();
            
            if (selectedIndex < 0 || activeAdmissions == null || activeAdmissions.isEmpty()) {
                messageLabel.setText("Please select a valid admission!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            Admission admission = activeAdmissions.get(selectedIndex);
            
            // Get room information to get price per day
            Room room = RoomDAO.getRoomById(admission.getRoomId());
            
            if (room == null) {
                messageLabel.setText("Room not found!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Calculate number of days
            LocalDate dischargeDate = LocalDate.parse(this.dischargeDate.getText());
            long daysAdmitted = ChronoUnit.DAYS.between(admission.getAdmissionDate(), dischargeDate);
            
            if (daysAdmitted < 0) {
                messageLabel.setText("Discharge date must be after admission date!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            if (daysAdmitted == 0) {
                daysAdmitted = 1;  // At least 1 day charge
            }
            
            // Calculate room charge
            double roomCharge = room.getPricePerDay() * daysAdmitted;
            roomChargeField.setText(String.format("%.2f", roomCharge));
            
            messageLabel.setText("Admission details loaded! Days: " + daysAdmitted);
            messageLabel.setForeground(new Color(0, 100, 150));
            
        } catch (Exception e) {
            messageLabel.setText("Error loading admission details!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle calculating total bill
     */
    private void handleCalculateBill() {
        try {
            // Get room charge
            String roomChargeText = roomChargeField.getText().trim();
            if (roomChargeText.isEmpty()) {
                messageLabel.setText("Load admission details first!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            double roomCharge = Double.parseDouble(roomChargeText);
            
            // Get doctor charge
            String doctorChargeText = doctorChargeField.getText().trim();
            if (doctorChargeText.isEmpty()) {
                messageLabel.setText("Please enter doctor charge!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            double doctorCharge = Double.parseDouble(doctorChargeText);
            
            if (doctorCharge < 0) {
                messageLabel.setText("Doctor charge cannot be negative!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Calculate total
            double totalCharge = roomCharge + doctorCharge;
            totalChargeField.setText(String.format("%.2f", totalCharge));
            
            messageLabel.setText("Bill calculated successfully!");
            messageLabel.setForeground(new Color(0, 150, 0));
            
        } catch (NumberFormatException e) {
            messageLabel.setText("Invalid charge amount!");
            messageLabel.setForeground(Color.RED);
        } catch (Exception e) {
            messageLabel.setText("Error calculating bill!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle discharging patient and generating bill
     */
    private void handleDischargePatient() {
        try {
            int selectedIndex = admissionCombo.getSelectedIndex();
            
            if (selectedIndex < 0 || activeAdmissions == null || activeAdmissions.isEmpty()) {
                messageLabel.setText("Please select a valid admission!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Validate inputs
            String dischargeDateText = dischargeDate.getText().trim();
            String roomChargeText = roomChargeField.getText().trim();
            String doctorChargeText = doctorChargeField.getText().trim();
            String totalChargeText = totalChargeField.getText().trim();
            
            if (dischargeDateText.isEmpty() || roomChargeText.isEmpty() || 
                doctorChargeText.isEmpty() || totalChargeText.isEmpty()) {
                messageLabel.setText("Please load and calculate bill first!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            Admission admission = activeAdmissions.get(selectedIndex);
            LocalDate dischargeLocalDate = LocalDate.parse(dischargeDateText);
            double roomCharge = Double.parseDouble(roomChargeText);
            double doctorCharge = Double.parseDouble(doctorChargeText);
            double totalCharge = Double.parseDouble(totalChargeText);
            
            // Step 1: Discharge patient (update admission with discharge date)
            if (!AdmissionDAO.dischargePatient(admission.getId(), dischargeLocalDate)) {
                messageLabel.setText("Failed to discharge patient!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Step 2: Generate bill
            Bill bill = new Bill(admission.getId(), roomCharge, doctorCharge, totalCharge, LocalDate.now());
            
            if (!BillDAO.addBill(bill)) {
                messageLabel.setText("Failed to generate bill!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Success!
            messageLabel.setText("✓ Patient discharged successfully! Bill generated.");
            messageLabel.setForeground(new Color(0, 150, 0));
            
            // Clear fields
            roomChargeField.setText("");
            doctorChargeField.setText("500");
            totalChargeField.setText("");
            dischargeDate.setText(LocalDate.now().toString());
            
            // Reload admissions
            loadActiveAdmissions();
            
            // Show billing history
            handleViewBills();
            
        } catch (Exception e) {
            messageLabel.setText("Error discharging patient!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle viewing all bills
     */
    private void handleViewBills() {
        try {
            tableModel.setRowCount(0);
            
            // Get all bills
            List<Bill> bills = BillDAO.getAllBills();
            
            for (Bill bill : bills) {
                Admission admission = AdmissionDAO.getAdmissionById(bill.getAdmissionId());
                String patientName = "Unknown";
                
                if (admission != null) {
                    Patient patient = PatientDAO.getPatientById(admission.getPatientId());
                    if (patient != null) {
                        patientName = patient.getName();
                    }
                }
                
                Object[] row = {
                    bill.getId(),
                    bill.getAdmissionId(),
                    patientName,
                    String.format("%.2f", bill.getRoomCharge()),
                    String.format("%.2f", bill.getDoctorCharge()),
                    String.format("%.2f", bill.getTotalCharge()),
                    bill.getBillDate()
                };
                tableModel.addRow(row);
            }
            
        } catch (Exception e) {
            messageLabel.setText("Error loading bills!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
}