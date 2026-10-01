package com.hospital.dao;


import com.hospital.db.DatabaseConnection;
import com.hospital.models.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {
    
    /**
     * Add a new room to the database
     * @param room Room object to add
     * @return true if successful, false if failed
     */
    public static boolean addRoom(Room room) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return false;
            }
            
            String query = "INSERT INTO rooms (room_number, room_type, price_per_day, status) VALUES (?, ?, ?, ?)";
            pst = conn.prepareStatement(query);
            
            pst.setString(1, room.getRoomNumber());
            pst.setString(2, room.getRoomType());
            pst.setDouble(3, room.getPricePerDay());
            pst.setString(4, room.getStatus());
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Room added successfully!");
                return true;
            } else {
                System.out.println("✗ Failed to add room!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while adding room!");
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
     * Get all rooms from the database
     * @return List of all rooms
     */
    public static List<Room> getAllRooms() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Room> rooms = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                System.out.println("✗ Database connection failed!");
                return rooms;
            }
            
            String query = "SELECT id, room_number, room_type, price_per_day, status FROM rooms ORDER BY id DESC";
            pst = conn.prepareStatement(query);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String roomNumber = rs.getString("room_number");
                String roomType = rs.getString("room_type");
                double pricePerDay = rs.getDouble("price_per_day");
                String status = rs.getString("status");
                
                Room room = new Room(id, roomNumber, roomType, pricePerDay, status);
                rooms.add(room);
            }
            
            System.out.println("✓ Retrieved " + rooms.size() + " rooms from database!");
            return rooms;
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving rooms!");
            e.printStackTrace();
            return rooms;
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
     * Get available rooms only
     * @return List of available rooms
     */
    public static List<Room> getAvailableRooms() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        List<Room> rooms = new ArrayList<>();
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return rooms;
            }
            
            String query = "SELECT id, room_number, room_type, price_per_day, status FROM rooms WHERE status = 'AVAILABLE' ORDER BY id DESC";
            pst = conn.prepareStatement(query);
            
            rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("id");
                String roomNumber = rs.getString("room_number");
                String roomType = rs.getString("room_type");
                double pricePerDay = rs.getDouble("price_per_day");
                String status = rs.getString("status");
                
                Room room = new Room(id, roomNumber, roomType, pricePerDay, status);
                rooms.add(room);
            }
            
            return rooms;
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving available rooms!");
            e.printStackTrace();
            return rooms;
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
     * Get room by ID
     * @param roomId Room ID to search
     * @return Room object if found, null if not found
     */
    public static Room getRoomById(int roomId) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return null;
            }
            
            String query = "SELECT id, room_number, room_type, price_per_day, status FROM rooms WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, roomId);
            
            rs = pst.executeQuery();
            
            if (rs.next()) {
                int id = rs.getInt("id");
                String roomNumber = rs.getString("room_number");
                String roomType = rs.getString("room_type");
                double pricePerDay = rs.getDouble("price_per_day");
                String status = rs.getString("status");
                
                System.out.println("✓ Room found!");
                return new Room(id, roomNumber, roomType, pricePerDay, status);
            } else {
                System.out.println("✗ Room not found!");
                return null;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while retrieving room!");
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
     * Update room status
     * @param roomId Room ID to update
     * @param status New status (AVAILABLE or OCCUPIED)
     * @return true if successful, false if failed
     */
    public static boolean updateRoomStatus(int roomId, String status) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return false;
            }
            
            String query = "UPDATE rooms SET status = ? WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setString(1, status);
            pst.setInt(2, roomId);
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Room status updated successfully!");
                return true;
            } else {
                System.out.println("✗ Room not found!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while updating room!");
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
     * Delete room by ID
     * @param roomId Room ID to delete
     * @return true if successful, false if failed
     */
    public static boolean deleteRoom(int roomId) {
        Connection conn = null;
        PreparedStatement pst = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            
            if (conn == null) {
                return false;
            }
            
            String query = "DELETE FROM rooms WHERE id = ?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, roomId);
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✓ Room deleted successfully!");
                return true;
            } else {
                System.out.println("✗ Room not found!");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Database error while deleting room!");
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