package com.project.paymentservice.dto;

public class PaymentResponseDto {
    private String orderID;
    private String approvalLink;

    // Getters and Setters
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getApprovalLink() {
        return approvalLink;
    }

    public void setApprovalLink(String approvalLink) {
        this.approvalLink = approvalLink;
    }

    @Override
    public String toString() {
        return "PaymentResponseDto{" +
                "orderID='" + orderID + '\'' +
                ", approvalLink='" + approvalLink + '\'' +
                '}';
    }
}
