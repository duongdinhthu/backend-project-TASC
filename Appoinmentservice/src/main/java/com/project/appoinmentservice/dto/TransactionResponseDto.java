package com.project.appoinmentservice.dto;

public class TransactionResponseDto {
    private String orderID;
    private String approvalLink;

    // Constructor
    public TransactionResponseDto() {
        this.orderID = orderID;
        this.approvalLink = approvalLink;
    }

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
        return "TransactionResponseDto{" +
                "orderID='" + orderID + '\'' +
                ", approvalLink='" + approvalLink + '\'' +
                '}';
    }
}
