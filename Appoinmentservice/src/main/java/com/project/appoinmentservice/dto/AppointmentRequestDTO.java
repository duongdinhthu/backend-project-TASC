package com.project.appoinmentservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AppointmentRequestDTO {
    private Integer patientId;
    private Integer doctorId;
    private Integer staffId;
    private LocalDate appointmentDate; // Chỉ chứa ngày
    private LocalDate medicalDay;      // Chỉ chứa ngày
    private Integer slot;
    private String status;
    private BigDecimal price;          // Giá dịch vụ
    private String patientEmail;
    private String patientPhone;
    private String patientName;
    private String paymentCurrency;
    private String paymentEncryption;

    // Getter và Setter
    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    @Override
    public String toString() {
        return "AppointmentRequestDTO{" +
                " patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", staffId=" + staffId +
                ", appointmentDate=" + appointmentDate +
                ", medicalDay=" + medicalDay +
                ", slot=" + slot +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", patientEmail='" + patientEmail + '\'' +
                ", patientPhone='" + patientPhone + '\'' +
                ", patientName='" + patientName + '\'' +
                ", paymentCurrency='" + paymentCurrency + '\'' +
                ", paymentEncryption='" + paymentEncryption + '\'' +
                '}';
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalDate getMedicalDay() {
        return medicalDay;
    }

    public void setMedicalDay(LocalDate medicalDay) {
        this.medicalDay = medicalDay;
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

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public String getPaymentEncryption() {
        return paymentEncryption;
    }

    public void setPaymentEncryption(String paymentEncryption) {
        this.paymentEncryption = paymentEncryption;
    }
}
