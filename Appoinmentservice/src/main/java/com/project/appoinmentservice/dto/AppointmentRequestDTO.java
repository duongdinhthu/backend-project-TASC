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
    private String patientEmail;
    private String patientPhone;
    private String patientName;
    private BigDecimal paymentAmount;  // Số tiền thanh toán
    private String randomCode; // Mã random

    public AppointmentRequestDTO() {
    }

    public Integer getPatientId() {
        return patientId;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
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




    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "AppointmentRequestDTO{" +
                "patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", staffId=" + staffId +
                ", appointmentDate=" + appointmentDate +
                ", medicalDay=" + medicalDay +
                ", slot=" + slot +
                ", status='" + status + '\'' +
                ", patientEmail='" + patientEmail + '\'' +
                ", patientPhone='" + patientPhone + '\'' +
                ", patientName='" + patientName + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", randomCode='" + randomCode + '\'' +
                '}';
    }
}
