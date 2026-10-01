
package com.hospital.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Nikhil@2005";  // Change if your MySQL password is different
    
    // Static variable to hold the connection
    private static Connection connection = null;
    
    /**
     * Get a connection to the database
     * If connection is null or closed, create a new one
     */
    public static Connection getConnection() {
        try {
            // Check if connection is null or closed
            if (connection == null || connection.isClosed()) {
                // Load MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Create new connection
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("✓ Database connected successfully!");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("✗ MySQL Driver not found!");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("✗ Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Close the database connection
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed!");
            }
        } catch (SQLException e) {
            System.out.println("✗ Error closing connection!");
            e.printStackTrace();
        }
    }
    
}
