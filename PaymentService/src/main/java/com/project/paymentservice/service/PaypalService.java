package com.project.paymentservice.service;

import com.project.paymentservice.dto.PaymentAmountDto;
import com.project.paymentservice.dto.PaymentResponseDto;
import org.springframework.stereotype.Service;


@Service
public interface PaypalService {
    public boolean verifyPayment(String orderID) ;
    public PaymentResponseDto createPayment(PaymentAmountDto paymentAmountDto);
}
