package com.project.transactionn.dto;

import java.math.BigDecimal;

public class PaymentAmountDto {
    private BigDecimal paymentAmount;

    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
