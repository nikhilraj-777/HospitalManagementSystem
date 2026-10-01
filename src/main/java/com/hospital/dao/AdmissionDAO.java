package com.hospital.dao;

import com.hospital.db.DatabaseConnection;
import com.hospital.models.Admission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AdmissionDAO {
    
    /**
     * Add a new admission to the database
     * Also updates room status to OCCUPIED
     * @param admission Admission object to add
     * @return true if successful, false if failed
     */
    public static boolean addAdmission(Admission admission) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return false;
            }
            
            // Insert admission record
            String query = "INSERT INTO admissions (patient_id, room_id, admission_date, discharge_date) VALUES (?, ?, ?, ?)";
            pst = conn.prepareStatement(query);
            
            pst.setInt(1, admission.getPatientId());
            pst.setInt(2, admission.getRoomId());
            pst.setObject(3, admission.getAdmissionDate());
            pst.setObject(4, admission.getDischargeDate());
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                // Update room status to OCCUPIED
                if (RoomDAO.updateRoomStatus(admission.getRoomId(), "OCCUPIED")) {
                    System.out.println("✓ Patient admitted successfully!");
                    return true;
                } else {
                    System.out.println("✗ Failed to update room status!");
                    return false;
                }
            } else {
                System.out.println("✗ Failed to add admission!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while adding admission!");
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
     * Get all admissions from the database
     * @return List of all admissions
     */
    public static List<Admission> getAllAdmissions() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Admission> admissions = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return admissions;
            }
            
            String query = "SELECT id, patient_id, room_id, admission_date, discharge_date FROM admissions ORDER BY id DESC";
            pst = conn.prepareStatement(query);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int patientId = rs.getInt("patient_id");
                int roomId = rs.getInt("room_id");
                LocalDate admissionDate = rs.getObject("admission_date", LocalDate.class);
                LocalDate dischargeDate = rs.getObject("discharge_date", LocalDate.class);
                
                Admission admission = new Admission(id, patientId, roomId, admissionDate, dischargeDate);
                admissions.add(admission);
            }
            
            System.out.println("✓ Retrieved " + admissions.size() + " admissions from database!");
            return admissions;
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving admissions!");
            e.printStackTrace();
            return admissions;
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
     * Get active admissions (not yet discharged)
     * @return List of active admissions
     */
    public static List<Admission> getActiveAdmissions() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Admission> admissions = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return admissions;
            }
            
            String query = "SELECT id, patient_id, room_id, admission_date, discharge_date FROM admissions WHERE discharge_date IS NULL ORDER BY id DESC";
            pst = conn.prepareStatement(query);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                int patientId = rs.getInt("patient_id");
                int roomId = rs.getInt("room_id");
                LocalDate admissionDate = rs.getObject("admission_date", LocalDate.class);
                LocalDate dischargeDate = rs.getObject("discharge_date", LocalDate.class);
                
                Admission admission = new Admission(id, patientId, roomId, admissionDate, dischargeDate);
                admissions.add(admission);
            }
            
            return admissions;
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving active admissions!");
            e.printStackTrace();
            return admissions;
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
     * Get admission by ID
     * @param admissionId Admission ID to search
     * @return Admission object if found, null if not found
     */
    public static Admission getAdmissionById(int admissionId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return null;
            }
            
            String query = "SELECT id, patient_id, room_id, admission_date, discharge_date FROM admissions WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, admissionId);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                int patientId = rs.getInt("patient_id");
                int roomId = rs.getInt("room_id");
                LocalDate admissionDate = rs.getObject("admission_date", LocalDate.class);
                LocalDate dischargeDate = rs.getObject("discharge_date", LocalDate.class);
                
                System.out.println("✓ Admission found!");
                return new Admission(id, patientId, roomId, admissionDate, dischargeDate);
            } else {
                System.out.println("✗ Admission not found!");
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving admission!");
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
     * Update admission with discharge date
     * @param admissionId Admission ID to update
     * @param dischargeDate Discharge date
     * @return true if successful, false if failed
     */
    public static boolean dischargePatient(int admissionId, LocalDate dischargeDate) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return false;
            }
            
            // First, get the room_id from admission
            String selectQuery = "SELECT room_id FROM admissions WHERE id = ?";
            pst = conn.prepareStatement(selectQuery);
            pst.setInt(1, admissionId);
            rs = pst.executeQuery();
            
            int roomId = -1;
            if (rs.next()) {
                roomId = rs.getInt("room_id");
            } else {
                System.out.println("✗ Admission not found!");
                return false;
            }
            
            // Update admission with discharge date
            String updateQuery = "UPDATE admissions SET discharge_date = ? WHERE id = ?";
            pst = conn.prepareStatement(updateQuery);
            pst.setObject(1, dischargeDate);
            pst.setInt(2, admissionId);
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                // Update room status back to AVAILABLE
                if (RoomDAO.updateRoomStatus(roomId, "AVAILABLE")) {
                    System.out.println("✓ Patient discharged successfully!");
                    return true;
                } else {
                    System.out.println("✗ Failed to update room status!");
                    return false;
                }
            } else {
                System.out.println("✗ Failed to discharge patient!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while discharging patient!");
            e.printStackTrace();
            return false;
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