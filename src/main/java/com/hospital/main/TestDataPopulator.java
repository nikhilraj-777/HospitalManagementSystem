package com.hospital.main;

import com.hospital.dao.DoctorDAO;
import com.hospital.dao.PatientDAO;
import com.hospital.dao.RoomDAO;
import com.hospital.dao.AdmissionDAO;
import com.hospital.db.DatabaseConnection;
import com.hospital.models.Doctor;
import com.hospital.models.Patient;
import com.hospital.models.Room;
import com.hospital.models.Admission;
import java.time.LocalDate;

public class TestDataPopulator {
    
    /**
     * Populate database with test data
     */
    public static void populateTestData() {
        System.out.println("\n=== Populating Test Data ===\n");
        
        // Add test doctors
        System.out.println("Adding test doctors...");
        addTestDoctors();
        
        // Add test rooms
        System.out.println("\nAdding test rooms...");
        addTestRooms();
        
        // Add test patients
        System.out.println("\nAdding test patients...");
        addTestPatients();
        
        // Add test admissions
        System.out.println("\nAdding test admissions...");
        addTestAdmissions();
        
        System.out.println("\n=== Test Data Population Complete ===\n");
        
        // Close database connection
        DatabaseConnection.closeConnection();
    }
    
    /**
     * Add test doctors
     */
    private static void addTestDoctors() {
        Doctor[] doctors = {
            new Doctor("Dr. Rajesh Kumar", "Cardiology", "9876543210"),
            new Doctor("Dr. Priya Singh", "Orthopedics", "9123456789"),
            new Doctor("Dr. Amit Patel", "Neurology", "9876541230"),
            new Doctor("Dr. Neha Sharma", "Pediatrics", "9654321098"),
            new Doctor("Dr. Vikram Reddy", "General Medicine", "9234567890"),
            new Doctor("Dr. Anjali Verma", "Gynecology", "9345678901")
        };
        
        for (Doctor doctor : doctors) {
            if (DoctorDAO.addDoctor(doctor)) {
                System.out.println("  ✓ Added: " + doctor.getName());
            }
        }
    }
    
    /**
     * Add test rooms
     */
    private static void addTestRooms() {
        Room[] rooms = {
            new Room("101", "General", 1000.00, "AVAILABLE"),
            new Room("102", "General", 1000.00, "AVAILABLE"),
            new Room("103", "General", 1000.00, "AVAILABLE"),
            new Room("104", "General", 1000.00, "AVAILABLE"),
            new Room("105", "General", 1000.00, "AVAILABLE"),
            new Room("201", "ICU", 3000.00, "AVAILABLE"),
            new Room("202", "ICU", 3000.00, "AVAILABLE"),
            new Room("203", "ICU", 3000.00, "AVAILABLE"),
            new Room("204", "ICU", 3000.00, "AVAILABLE"),
            new Room("205", "ICU", 3000.00, "AVAILABLE")
        };
        
        for (Room room : rooms) {
            if (RoomDAO.addRoom(room)) {
                System.out.println("  ✓ Added: Room " + room.getRoomNumber() + " (" + room.getRoomType() + ")");
            }
        }
    }
    
    /**
     * Add test patients
     */
    private static void addTestPatients() {
        Patient[] patients = {
            new Patient("Nikhil Kumar", 28, "MALE", "9876543210"),
            new Patient("Priya Sharma", 32, "FEMALE", "9123456789"),
            new Patient("Amit Singh", 45, "MALE", "9876541230"),
            new Patient("Anjali Verma", 38, "FEMALE", "9654321098"),
            new Patient("Rajesh Patel", 55, "MALE", "9234567890"),
            new Patient("Deepika Reddy", 26, "FEMALE", "9345678901"),
            new Patient("Sanjay Nair", 50, "MALE", "9456789012"),
            new Patient("Neha Gupta", 35, "FEMALE", "9567890123"),
            new Patient("Vikram Jain", 42, "MALE", "9678901234"),
            new Patient("Pooja Singh", 29, "FEMALE", "9789012345")
        };
        
        for (Patient patient : patients) {
            if (PatientDAO.addPatient(patient)) {
                System.out.println("  ✓ Added: " + patient.getName());
            }
        }
    }
    
    /**
     * Add test admissions
     * This creates some sample admissions to demonstrate the system
     */
    private static void addTestAdmissions() {
        // Note: These admissions assume patient IDs 1-10 and room IDs 1-10 exist
        // Adjust IDs if necessary based on your database
        
        Admission[] admissions = {
            // Patient 1 admitted to Room 1 (General) - 5 days ago
            new Admission(1, 1, LocalDate.now().minusDays(5), null),
            
            // Patient 2 admitted to Room 6 (ICU) - 3 days ago
            new Admission(2, 6, LocalDate.now().minusDays(3), null),
            
            // Patient 3 admitted to Room 2 (General) - 7 days ago, discharged 2 days ago
            new Admission(3, 2, LocalDate.now().minusDays(7), LocalDate.now().minusDays(2)),
            
            // Patient 4 admitted to Room 7 (ICU) - 1 day ago
            new Admission(4, 7, LocalDate.now().minusDays(1), null)
        };
        
        for (Admission admission : admissions) {
            if (AdmissionDAO.addAdmission(admission)) {
                System.out.println("  ✓ Added: Admission for Patient " + admission.getPatientId() + 
                    " to Room " + admission.getRoomId());
            }
        }
    }
    
    /**
     * Main method to run the test data populator
     */
    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("Hospital Management System");
        System.out.println("Test Data Populator");
        System.out.println("=========================================");
        
        populateTestData();
        
        System.out.println("=========================================");
        System.out.println("Press Enter to exit...");
        System.out.println("=========================================");
        
        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}