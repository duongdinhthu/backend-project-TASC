package com.project.appoinmentservice.dto;

import java.math.BigDecimal;

public class AppoinmntTransactionDTO {
    private Integer appointmentId;
    private String status;
    private BigDecimal paymentAmount;  // Số tiền thanh toán
    private String randomCode; // Mã random

    public String getRandomCode() {
        return randomCode;
    }

    public void setRandomCode(String randomCode) {
        this.randomCode = randomCode;
    }

    public Integer getAppointmentId() {
        return appointmentId;
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
        return "AppoinmntTransactionDTO{" +
                "appointmentId=" + appointmentId +
                ", status='" + status + '\'' +
                ", orderID='" + paymentAmount + '\'' +
                ", randomCode='" + randomCode + '\'' +
                '}';
    }
}
