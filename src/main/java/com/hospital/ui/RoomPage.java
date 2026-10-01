package com.hospital.ui;

import com.hospital.dao.RoomDAO;
import com.hospital.models.Room;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomPage extends JFrame {
    
    private JTextField roomNumberField;
    private JComboBox<String> roomTypeCombo;
    private JTextField pricePerDayField;
    private JButton addButton;
    private JButton viewAllButton;
    private JButton viewAvailableButton;
    private JButton backButton;
    private JLabel messageLabel;
    private JTable roomTable;
    private DefaultTableModel tableModel;
    private JScrollPane scrollPane;
    
    public RoomPage() {
        setTitle("Hospital Management System - Room Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(50, 120, 200));
        JLabel titleLabel = new JLabel("ROOM MANAGEMENT");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headerPanel.add(titleLabel);
        
        // Form panel for adding rooms
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Room"));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Room Number
        JLabel roomNumberLabel = new JLabel("Room Number:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(roomNumberLabel, gbc);
        
        roomNumberField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(roomNumberField, gbc);
        
        // Room Type
        JLabel roomTypeLabel = new JLabel("Room Type:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(roomTypeLabel, gbc);
        
        roomTypeCombo = new JComboBox<>(new String[]{"General", "ICU"});
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        formPanel.add(roomTypeCombo, gbc);
        
        // Price Per Day
        JLabel priceLabel = new JLabel("Price/Day:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        formPanel.add(priceLabel, gbc);
        
        pricePerDayField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        formPanel.add(pricePerDayField, gbc);
        
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
        
        addButton = new JButton("Add Room");
        addButton.setFont(new Font("Arial", Font.BOLD, 11));
        addButton.setBackground(new Color(50, 150, 50));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> handleAddRoom());
        
        viewAllButton = new JButton("View All Rooms");
        viewAllButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewAllButton.setBackground(new Color(50, 120, 200));
        viewAllButton.setForeground(Color.WHITE);
        viewAllButton.addActionListener(e -> handleViewAllRooms());
        
        viewAvailableButton = new JButton("View Available Rooms");
        viewAvailableButton.setFont(new Font("Arial", Font.BOLD, 11));
        viewAvailableButton.setBackground(new Color(100, 150, 200));
        viewAvailableButton.setForeground(Color.WHITE);
        viewAvailableButton.addActionListener(e -> handleViewAvailableRooms());
        
        backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 11));
        backButton.setBackground(new Color(200, 50, 50));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> this.dispose());
        
        buttonPanel.add(addButton);
        buttonPanel.add(viewAllButton);
        buttonPanel.add(viewAvailableButton);
        buttonPanel.add(backButton);
        
        // Table for displaying rooms
        String[] columns = {"ID", "Room Number", "Room Type", "Price/Day", "Status"};
        tableModel = new DefaultTableModel(columns, 0);
        roomTable = new JTable(tableModel);
        roomTable.setFont(new Font("Arial", Font.PLAIN, 11));
        roomTable.setRowHeight(25);
        roomTable.getTableHeader().setBackground(new Color(50, 120, 200));
        roomTable.getTableHeader().setForeground(Color.WHITE);
        
        scrollPane = new JScrollPane(roomTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Rooms List"));
        
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
     * Handle adding a new room
     */
    private void handleAddRoom() {
        try {
            // Validate input
            String roomNumber = roomNumberField.getText().trim();
            String roomType = (String) roomTypeCombo.getSelectedItem();
            String priceText = pricePerDayField.getText().trim();
            
            if (roomNumber.isEmpty() || priceText.isEmpty()) {
                messageLabel.setText("Please fill all fields!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Parse price
            double price;
            try {
                price = Double.parseDouble(priceText);
                if (price < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                messageLabel.setText("Please enter a valid price!");
                messageLabel.setForeground(Color.RED);
                return;
            }
            
            // Create room object with AVAILABLE status
            Room room = new Room(roomNumber, roomType, price, "AVAILABLE");
            
            // Add to database
            if (RoomDAO.addRoom(room)) {
                messageLabel.setText("Room added successfully!");
                messageLabel.setForeground(new Color(0, 150, 0));
                
                // Clear fields
                roomNumberField.setText("");
                pricePerDayField.setText("");
                roomTypeCombo.setSelectedIndex(0);
                
                // Refresh table
                handleViewAllRooms();
            } else {
                messageLabel.setText("Failed to add room! Try again.");
                messageLabel.setForeground(Color.RED);
            }
            
        } catch (Exception e) {
            messageLabel.setText("An error occurred!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle viewing all rooms
     */
    private void handleViewAllRooms() {
        try {
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get all rooms from database
            List<Room> rooms = RoomDAO.getAllRooms();
            
            // Add rows to table
            for (Room room : rooms) {
                Object[] row = {
                    room.getId(),
                    room.getRoomNumber(),
                    room.getRoomType(),
                    "₹" + room.getPricePerDay(),
                    room.getStatus()
                };
                tableModel.addRow(row);
            }
            
            messageLabel.setText("Loaded " + rooms.size() + " rooms!");
            messageLabel.setForeground(new Color(0, 150, 0));
            
        } catch (Exception e) {
            messageLabel.setText("Error loading rooms!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
    
    /**
     * Handle viewing available rooms only
     */
    private void handleViewAvailableRooms() {
        try {
            // Clear existing rows
            tableModel.setRowCount(0);
            
            // Get available rooms from database
            List<Room> rooms = RoomDAO.getAvailableRooms();
            
            // Add rows to table
            for (Room room : rooms) {
                Object[] row = {
                    room.getId(),
                    room.getRoomNumber(),
                    room.getRoomType(),
                    "₹" + room.getPricePerDay(),
                    room.getStatus()
                };
                tableModel.addRow(row);
            }
            
            messageLabel.setText("Loaded " + rooms.size() + " available rooms!");
            messageLabel.setForeground(new Color(0, 150, 0));
            
        } catch (Exception e) {
            messageLabel.setText("Error loading available rooms!");
            messageLabel.setForeground(Color.RED);
            e.printStackTrace();
        }
    }
}