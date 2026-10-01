package com.hospital.dao;
import com.hospital.db.DatabaseConnection;
import com.hospital.models.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    
    /**
     * Authenticate user by checking username and password
     * @param username Username to check
     * @param password Password to check
     * @return User object if login successful, null if failed
     */
    public static User loginUser(String username, String password) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            // Get connection from DatabaseConnection
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return null;
            }
            
            // SQL query to find user with matching username and password
            String query = "SELECT id, username, password, role FROM users WHERE username = ? AND password = ?";
            
            // Create PreparedStatement to prevent SQL injection
            pst = conn.prepareStatement(query);
            pst.setString(1, username);  // Set username parameter
            pst.setString(2, password);  // Set password parameter
            
            // Execute query
            rs = pst.executeQuery();
            
            // If user found
            if (rs.next()) {
                int id = rs.getInt("id");
                String dbUsername = rs.getString("username");
                String dbPassword = rs.getString("password");
                String role = rs.getString("role");
                
                System.out.println("✓ Login successful for user: " + dbUsername);
                
                // Return User object
                return new User(id, dbUsername, dbPassword, role);
            } else {
                System.out.println("✗ Invalid username or password!");
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error during login!");
            e.printStackTrace();
            return null;
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Get user by ID
     * @param userId User ID to search
     * @return User object if found, null if not found
     */
    public static User getUserById(int userId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return null;
            }
            
            String query = "SELECT id, username, password, role FROM users WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                
                return new User(id, username, password, role);
            }
            
            return null;
            
        } catch (SQLException e) {
            System.out.println("✗ Error retrieving user!");
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