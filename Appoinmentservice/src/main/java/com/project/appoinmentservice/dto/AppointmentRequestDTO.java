package com.project.appoinmentservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class AppointmentRequestDTO {
    private Integer appointmentId;
    private String patientId;
    private String doctorId;
    private String staffId;
    private Date appointmentDate;
    private Date medicalDay;
    private Integer slot;
    private String status;
    private BigDecimal price;
    private String patientEmail;
    private String patientPhone;
    private String patientName;
    private BigDecimal paymentAmount;
    private String paymentCurrency;
    private String paymentEncryption;
    // Getter, Setter

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public String getPaymentEncryption() {
        return paymentEncryption;
    }

    public void setPaymentEncryption(String paymentEncryption) {
        this.paymentEncryption = paymentEncryption;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getMedicalDay() {
        return medicalDay;
    }

    public void setMedicalDay(Date medicalDay) {
        this.medicalDay = medicalDay;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
