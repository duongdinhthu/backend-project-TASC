package com.project.paymentservice.dto;

import java.math.BigDecimal;

public class PaymentAmountDto {
    private BigDecimal paymentAmount;

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "PaymentAmountDto{" +
                "paymentAmount=" + paymentAmount +
                '}';
    }
}
