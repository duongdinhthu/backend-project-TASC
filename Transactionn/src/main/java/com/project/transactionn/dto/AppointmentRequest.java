package com.project.transactionn.dto;

import java.math.BigDecimal;

public class AppointmentRequest {
    private Integer appointmentId;
    private String status;
    private BigDecimal paymentAmount;
    private String randomCode; // Mã random

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "AppontmentRequest{" +
                "appointmentId=" + appointmentId +
                ", status='" + status + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", randomCode='" + randomCode + '\'' +
                '}';
    }
}
