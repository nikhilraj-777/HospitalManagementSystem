
package com.hospital.models;


import java.time.LocalDate;

public class Bill {
    
    private int id;
    private int admissionId;
    private double roomCharge;
    private double doctorCharge;
    private double totalCharge;
    private LocalDate billDate;
    
    // Constructor 1: With all fields
    public Bill(int id, int admissionId, double roomCharge, double doctorCharge, double totalCharge, LocalDate billDate) {
        this.id = id;
        this.admissionId = admissionId;
        this.roomCharge = roomCharge;
        this.doctorCharge = doctorCharge;
        this.totalCharge = totalCharge;
        this.billDate = billDate;
    }
    
    // Constructor 2: Without ID
    public Bill(int admissionId, double roomCharge, double doctorCharge, double totalCharge, LocalDate billDate) {
        this.admissionId = admissionId;
        this.roomCharge = roomCharge;
        this.doctorCharge = doctorCharge;
        this.totalCharge = totalCharge;
        this.billDate = billDate;
    }
    
    // Constructor 3: For calculations (before saving)
    public Bill(int admissionId, double roomCharge, double doctorCharge) {
        this.admissionId = admissionId;
        this.roomCharge = roomCharge;
        this.doctorCharge = doctorCharge;
        this.totalCharge = roomCharge + doctorCharge;  // Auto-calculate
        this.billDate = LocalDate.now();
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getAdmissionId() {
        return admissionId;
    }
    
    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
    }
    
    public double getRoomCharge() {
        return roomCharge;
    }
    
    public void setRoomCharge(double roomCharge) {
        this.roomCharge = roomCharge;
    }
    
    public double getDoctorCharge() {
        return doctorCharge;
    }
    
    public void setDoctorCharge(double doctorCharge) {
        this.doctorCharge = doctorCharge;
    }
    
    public double getTotalCharge() {
        return totalCharge;
    }
    
    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }
    
    public LocalDate getBillDate() {
        return billDate;
    }
    
    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }
    
    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", admissionId=" + admissionId +
                ", roomCharge=" + roomCharge +
                ", doctorCharge=" + doctorCharge +
                ", totalCharge=" + totalCharge +
                ", billDate=" + billDate +
                '}';
    }
}