package com.project.appoinmentservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentRequest {
    private Integer patientId;
    private Integer appointmentId;
    private BigDecimal paymentAmount;
    private String paymentCurrency;
    private String paymentEncryption;
    // Getters and setters



    public String getPaymentEncryption() {
        return paymentEncryption;
    }

    public void setPaymentEncryption(String paymentEncryption) {
        this.paymentEncryption = paymentEncryption;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
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



    @Override
    public String toString() {
        return "PaymentRequest{" +
                "patientId=" + patientId +
                ", appointmentId=" + appointmentId +
                ", paymentAmount=" + paymentAmount +
                ", paymentCurrency='" + paymentCurrency + '\'' +
                '}';
    }
}
