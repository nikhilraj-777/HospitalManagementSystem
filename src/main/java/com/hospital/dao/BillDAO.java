package com.hospital.dao;

import com.hospital.db.DatabaseConnection;
import com.hospital.models.Bill;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    
    /**
     * Add a new bill to the database
     * @param bill Bill object to add
     * @return true if successful, false if failed
     */
    public static boolean addBill(Bill bill) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return false;
            }
            
            String query = "INSERT INTO bills (admission_id, room_charge, doctor_charge, total_charge, bill_date) VALUES (?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, bill.getAdmissionId());
            pst.setDouble(2, bill.getRoomCharge());
            pst.setDouble(3, bill.getDoctorCharge());
            pst.setDouble(4, bill.getTotalCharge());
            pst.setObject(5, bill.getBillDate());
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Bill created successfully!");
                return true;
            } else {
                System.out.println("✗ Failed to create bill!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while creating bill!");
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get all bills from the database
     * @return List of all bills
     */
    public static List<Bill> getAllBills() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Bill> bills = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return bills;
            }
            
            String query = "SELECT id, admission_id, room_charge, doctor_charge, total_charge, bill_date FROM bills ORDER BY id DESC";
            pst = conn.prepareStatement(query);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int admissionId = rs.getInt("admission_id");
                double roomCharge = rs.getDouble("room_charge");
                double doctorCharge = rs.getDouble("doctor_charge");
                double totalCharge = rs.getDouble("total_charge");
                LocalDate billDate = rs.getObject("bill_date", LocalDate.class);
                
                Bill bill = new Bill(id, admissionId, roomCharge, doctorCharge, totalCharge, billDate);
                bills.add(bill);
            }
            
            System.out.println("✓ Retrieved " + bills.size() + " bills from database!");
            return bills;
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving bills!");
            e.printStackTrace();
            return bills;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get bill by ID
     * @param billId Bill ID to search
     * @return Bill object if found, null if not found
     */
    public static Bill getBillById(int billId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return null;
            }
            
            String query = "SELECT id, admission_id, room_charge, doctor_charge, total_charge, bill_date FROM bills WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, billId);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                int admissionId = rs.getInt("admission_id");
                double roomCharge = rs.getDouble("room_charge");
                double doctorCharge = rs.getDouble("doctor_charge");
                double totalCharge = rs.getDouble("total_charge");
                LocalDate billDate = rs.getObject("bill_date", LocalDate.class);
                
                System.out.println("✓ Bill found!");
                return new Bill(id, admissionId, roomCharge, doctorCharge, totalCharge, billDate);
            } else {
                System.out.println("✗ Bill not found!");
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving bill!");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get bill by admission ID
     * @param admissionId Admission ID to search
     * @return Bill object if found, null if not found
     */
    public static Bill getBillByAdmissionId(int admissionId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return null;
            }
            
            String query = "SELECT id, admission_id, room_charge, doctor_charge, total_charge, bill_date FROM bills WHERE admission_id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, admissionId);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                int admId = rs.getInt("admission_id");
                double roomCharge = rs.getDouble("room_charge");
                double doctorCharge = rs.getDouble("doctor_charge");
                double totalCharge = rs.getDouble("total_charge");
                LocalDate billDate = rs.getObject("bill_date", LocalDate.class);
                
                return new Bill(id, admId, roomCharge, doctorCharge, totalCharge, billDate);
            }
            
            return null;
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving bill!");
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}