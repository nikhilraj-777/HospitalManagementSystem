package com.hospital.dao;


import com.hospital.db.DatabaseConnection;
import com.hospital.models.Doctor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    
    /**
     * Add a new doctor to the database
     * @param doctor Doctor object to add
     * @return true if successful, false if failed
     */
    public static boolean addDoctor(Doctor doctor) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return false;
            }
            
            String query = "INSERT INTO doctors (name, specialization, phone) VALUES (?, ?, ?)";
            pst = conn.prepareStatement(query);
            
            pst.setString(1, doctor.getName());
            pst.setString(2, doctor.getSpecialization());
            pst.setString(3, doctor.getPhone());
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Doctor added successfully!");
                return true;
            } else {
                System.out.println("✗ Failed to add doctor!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while adding doctor!");
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
     * Get all doctors from the database
     * @return List of all doctors
     */
    public static List<Doctor> getAllDoctors() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Doctor> doctors = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return doctors;
            }
            
            String query = "SELECT id, name, specialization, phone FROM doctors ORDER BY id DESC";
            pst = conn.prepareStatement(query);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String phone = rs.getString("phone");
                
                Doctor doctor = new Doctor(id, name, specialization, phone);
                doctors.add(doctor);
            }
            
            System.out.println("✓ Retrieved " + doctors.size() + " doctors from database!");
            return doctors;
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving doctors!");
            e.printStackTrace();
            return doctors;
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
     * Get doctor by ID
     * @param doctorId Doctor ID to search
     * @return Doctor object if found, null if not found
     */
    public static Doctor getDoctorById(int doctorId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return null;
            }
            
            String query = "SELECT id, name, specialization, phone FROM doctors WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, doctorId);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialization = rs.getString("specialization");
                String phone = rs.getString("phone");
                
                System.out.println("✓ Doctor found!");
                return new Doctor(id, name, specialization, phone);
            } else {
                System.out.println("✗ Doctor not found!");
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving doctor!");
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
     * Delete doctor by ID
     * @param doctorId Doctor ID to delete
     * @return true if successful, false if failed
     */
    public static boolean deleteDoctor(int doctorId) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return false;
            }
            
            String query = "DELETE FROM doctors WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, doctorId);
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Doctor deleted successfully!");
                return true;
            } else {
                System.out.println("✗ Doctor not found!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while deleting doctor!");
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
}