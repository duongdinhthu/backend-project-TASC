package com.project.userservice.dto;

import java.math.BigDecimal;

public class DoctorDTO {
    private int id;
    private String doctorName;
    private String doctorEmail;
    private String doctorPhone;
    private String doctorAddress;
    private String doctorDescription;
    private BigDecimal doctorPrice;
    private int departmentId;

    // Constructors
    public DoctorDTO(int id, String doctorName, String doctorDescription, BigDecimal doctorPrice, int departmentId) {
        this.id = id;
        this.doctorName = doctorName;
        this.doctorDescription = doctorDescription;
        this.doctorPrice = doctorPrice;
        this.departmentId = departmentId;
    }

    public DoctorDTO(Integer id, String doctorName, String doctorDescription, BigDecimal doctorPrice, Integer departmentId) {
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public String getDoctorDescription() {
        return doctorDescription;
    }

    public void setDoctorDescription(String doctorDescription) {
        this.doctorDescription = doctorDescription;
    }

    public BigDecimal getDoctorPrice() {
        return doctorPrice;
    }

    public void setDoctorPrice(BigDecimal doctorPrice) {
        this.doctorPrice = doctorPrice;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
