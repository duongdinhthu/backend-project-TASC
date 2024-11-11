package com.project.paymentservice.dto;

import com.project.paymentservice.model.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


public class PaymentDTO {


    private String patientId;

    private String appointmentId;

    private BigDecimal paymentAmount;

    private String paymentEncryption;

    private String paymentCurrency;

    private PaymentStatus paymentStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;




    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentEncryption() {
        return paymentEncryption;
    }

    public void setPaymentEncryption(String paymentEncryption) {
        this.paymentEncryption = paymentEncryption;
    }

    public String getPaymentCurrency() {
        return paymentCurrency;
    }

    public void setPaymentCurrency(String paymentCurrency) {
        this.paymentCurrency = paymentCurrency;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        return "PaymentDTO{" +
                "patientId=" + patientId +
                ", appointmentId=" + appointmentId +
                ", paymentAmount=" + paymentAmount +
                ", paymentEncryption='" + paymentEncryption + '\'' +
                ", paymentCurrency='" + paymentCurrency + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
