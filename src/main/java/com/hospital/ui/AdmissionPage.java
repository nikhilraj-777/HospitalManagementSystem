package com.hospital.ui;

import com.hospital.dao.AdmissionDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.RoomDAO;
import com.hospital.models.Admission;
import com.hospital.models.Patient;
import com.hospital.models.Room;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class AdmissionPage extends JFrame {
    
    private JComboBox<String> patientCombo;
    private JComboBox<String> roomCombo;
    private JButton admitButton;
    private JButton viewButton;
    private JButton backButton;
    private JLabel messageLabel;
    private JTable admissionTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    // Store patient and room objects for quick lookup
    private List<Patient> patients;
    private List<Room> availableRooms;
    
    public AdmissionPage() {
        setTitle("Hospital Management System - Patient Admission");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        JLabel titleLabel = new JLabel("PATIENT ADMISSION");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        
        // Form panel for admitting patient
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder("Admit Patient"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Patient selection
        JLabel patientLabel = new JLabel("Select Patient:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(patientLabel, gbc);
        
        patientCombo = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        formPanel.add(patientCombo, gbc);
        
        // Room selection
        JLabel roomLabel = new JLabel("Select Room:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(roomLabel, gbc);
        
        roomCombo = new JComboBox<>();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        formPanel.add(roomCombo, gbc);
        
        // Message label
        messageLabel = new JLabel("");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        messageLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        formPanel.add(messageLabel, gbc);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        admitButton = new JButton("Admit Patient");
        admitButton.setFont(new Font("Arial", Font.BOLD, 11));
        admitButton.setBackground(new Color(50, 150, 50));
        admitButton.setForeground(Color.WHITE);
        admitButton.addActionListener(e -> handleAdmitPatient());
        
        viewButton = new JButton("View All Admissions");
        viewButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewButton.setBackground(new Color(50, 120, 200));
        viewButton.setForeground(Color.WHITE);
        viewButton.addActionListener(e -> handleViewAdmissions());
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 11));
        backButton.setBackground(new Color(200, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> this.dispose());
        
        buttonPanel.add(admitButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(backButton);
        
        // Table for displaying admissions
        String[] columns = {"Admission ID", "Patient ID", "Patient Name", "Room ID", "Room Number", "Admission Date", "Discharge Date"};
        tableModel = new DefaultTableModel(columns, 0);
        admissionTable = new JTable(tableModel);
        admissionTable.setFont(new Font("Arial", Font.PLAIN, 10));
        admissionTable.setRowHeight(25);
        admissionTable.getTableHeader().setBackground(new Color(50, 120, 200));
        admissionTable.getTableHeader().setForeground(Color.WHITE);
        
        scrollPane = new JScrollPane(admissionTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Admissions List"));
        
        // Load patients and rooms on startup
        loadPatients();
        loadAvailableRooms();
        
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
     * Load all patients into combo box
     */
    private void loadPatients() {
        try {
            patientCombo.removeAllItems();
            patients = PatientDAO.getAllPatients();
            
            if (patients.isEmpty()) {
                patientCombo.addItem("No patients available");
            } else {
                for (Patient patient : patients) {
                    patientCombo.addItem("ID: " + patient.getId() + " - " + patient.getName());
                }
            }
        } catch (Exception e) {
            messageLabel.setText("Error loading patients!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Load all available rooms into combo box
     */
    private void loadAvailableRooms() {
        try {
            roomCombo.removeAllItems();
            availableRooms = RoomDAO.getAvailableRooms();
            
            if (availableRooms.isEmpty()) {
                roomCombo.addItem("No available rooms");
            } else {
                for (Room room : availableRooms) {
                    roomCombo.addItem("Room " + room.getRoomNumber() + " (" + room.getRoomType() + ") - ₹" + room.getPricePerDay());
                }
            }
        } catch (Exception e) {
            messageLabel.setText("Error loading rooms!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle admitting a patient
     */
    private void handleAdmitPatient() {
        try {
            // Validate selection
            if (patients == null || patients.isEmpty()) {
                messageLabel.setText("No patients available!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            if (availableRooms == null || availableRooms.isEmpty()) {
                messageLabel.setText("No available rooms!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Get selected patient and room
            int patientIndex = patientCombo.getSelectedIndex();
            int roomIndex = roomCombo.getSelectedIndex();
            
            if (patientIndex < 0 || roomIndex < 0) {
                messageLabel.setText("Please select patient and room!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            Patient selectedPatient = patients.get(patientIndex);
            Room selectedRoom = availableRooms.get(roomIndex);
            
            // Create admission with today's date
            Admission admission = new Admission(
                selectedPatient.getId(),
                selectedRoom.getId(),
                LocalDate.now()
            );
            
            // Add to database
            if (AdmissionDAO.addAdmission(admission)) {
                messageLabel.setText("Patient admitted successfully!");
                messageLabel.setForeground(new Color(0, 150, 0));
                
                // Reload rooms (refresh available rooms list)
                loadAvailableRooms();
                
                // Refresh table
                handleViewAdmissions();
            } else {
                messageLabel.setText("Failed to admit patient! Try again.");
                messageLabel.setForeground(Color.RED);
            }
            
        } catch (Exception e) {
            messageLabel.setText("An error occurred!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle viewing all admissions
     */
    private void handleViewAdmissions() {
        try {
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get all admissions from database
            List<Admission> admissions = AdmissionDAO.getAllAdmissions();
            
            // Add rows to table
            for (Admission admission : admissions) {
                // Get patient name
                Patient patient = PatientDAO.getPatientById(admission.getPatientId());
                String patientName = (patient != null) ? patient.getName() : "Unknown";
                
                // Get room number
                Room room = RoomDAO.getRoomById(admission.getRoomId());
                String roomNumber = (room != null) ? room.getRoomNumber() : "Unknown";
                
                Object[] row = {
                    admission.getId(),
                    admission.getPatientId(),
                    patientName,
                    admission.getRoomId(),
                    roomNumber,
                    admission.getAdmissionDate(),
                    (admission.getDischargeDate() != null) ? admission.getDischargeDate() : "Still Admitted"
                };
                tableModel.addRow(row);
            }
            
            messageLabel.setText("Loaded " + admissions.size() + " admissions!");
            messageLabel.setForeground(new Color(0, 150, 0));
            
        } catch (Exception e) {
            messageLabel.setText("Error loading admissions!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
}