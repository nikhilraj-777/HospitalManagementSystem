/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.hospital.models;


import java.time.LocalDate;

public class Admission {
    
    private int id;
    private int patientId;
    private int roomId;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;  // Can be null if still admitted
    
    // Constructor 1: With all fields
    public Admission(int id, int patientId, int roomId, LocalDate admissionDate, LocalDate dischargeDate) {
        this.id = id;
        this.patientId = patientId;
        this.roomId = roomId;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }
    
    // Constructor 2: Without ID
    public Admission(int patientId, int roomId, LocalDate admissionDate, LocalDate dischargeDate) {
        this.patientId = patientId;
        this.roomId = roomId;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }
    
    // Constructor 3: Without discharge date (currently admitted)
    public Admission(int patientId, int roomId, LocalDate admissionDate) {
        this.patientId = patientId;
        this.roomId = roomId;
        this.admissionDate = admissionDate;
        this.dischargeDate = null;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getPatientId() {
        return patientId;
    }
    
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    
    public int getRoomId() {
        return roomId;
    }
    
    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
    
    public LocalDate getAdmissionDate() {
        return admissionDate;
    }
    
    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }
    
    public LocalDate getDischargeDate() {
        return dischargeDate;
    }
    
    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }
    
    @Override
    public String toString() {
        return "Admission{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", roomId=" + roomId +
                ", admissionDate=" + admissionDate +
                ", dischargeDate=" + dischargeDate +
                '}';
    }
}