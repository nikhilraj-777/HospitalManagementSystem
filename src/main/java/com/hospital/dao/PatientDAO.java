package com.hospital.dao;

import com.hospital.db.DatabaseConnection;
import com.hospital.models.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    
    /**
     * Add a new patient to the database
     * @param patient Patient object to add
     * @return true if successful, false if failed
     */
    public static boolean addPatient(Patient patient) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return false;
            }
            
            String query = "INSERT INTO patients (name, age, gender, phone) VALUES (?, ?, ?, ?)";
            pst = conn.prepareStatement(query);
            
            pst.setString(1, patient.getName());
            pst.setInt(2, patient.getAge());
            pst.setString(3, patient.getGender());
            pst.setString(4, patient.getPhone());
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Patient added successfully!");
                return true;
            } else {
                System.out.println("✗ Failed to add patient!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while adding patient!");
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
     * Get all patients from the database
     * @return List of all patients
     */
    public static List<Patient> getAllPatients() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Patient> patients = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return patients;
            }
            
            String query = "SELECT id, name, age, gender, phone FROM patients ORDER BY id DESC";
            pst = conn.prepareStatement(query);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                
                Patient patient = new Patient(id, name, age, gender, phone);
                patients.add(patient);
            }
            
            System.out.println("✓ Retrieved " + patients.size() + " patients from database!");
            return patients;
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving patients!");
            e.printStackTrace();
            return patients;
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
     * Get patient by ID
     * @param patientId Patient ID to search
     * @return Patient object if found, null if not found
     */
    public static Patient getPatientById(int patientId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return null;
            }
            
            String query = "SELECT id, name, age, gender, phone FROM patients WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, patientId);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String gender = rs.getString("gender");
                String phone = rs.getString("phone");
                
                System.out.println("✓ Patient found!");
                return new Patient(id, name, age, gender, phone);
            } else {
                System.out.println("✗ Patient not found!");
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving patient!");
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
     * Delete patient by ID
     * @param patientId Patient ID to delete
     * @return true if successful, false if failed
     */
    public static boolean deletePatient(int patientId) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return false;
            }
            
            String query = "DELETE FROM patients WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, patientId);
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Patient deleted successfully!");
                return true;
            } else {
                System.out.println("✗ Patient not found!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while deleting patient!");
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