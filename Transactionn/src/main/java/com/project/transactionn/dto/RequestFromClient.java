package com.project.transactionn.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RequestFromClient {
    private Integer departmentId;
    private Integer doctorId;
    private LocalDate medicalDay;      // Chỉ chứa ngày
    private Integer slot;
    private String patientEmail;
    private String patientPhone;
    private String patientName;
    private BigDecimal paymentAmount;  // Số tiền thanh toán

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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
        return "RequestFromClient{" +
                "departmentId=" + departmentId +
                ", doctorId=" + doctorId +
                ", medicalDay=" + medicalDay +
                ", slot=" + slot +
                ", patientEmail='" + patientEmail + '\'' +
                ", patientPhone='" + patientPhone + '\'' +
                ", patientName='" + patientName + '\'' +
                ", paymentAmount=" + paymentAmount +
                '}';
    }
}
