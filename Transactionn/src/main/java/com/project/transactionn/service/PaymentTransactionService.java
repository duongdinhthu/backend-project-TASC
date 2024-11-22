package com.project.transactionn.service;

import com.project.transactionn.dto.PaymentAmountDto;
import com.project.transactionn.dto.PaymentResponseDto;
import com.project.transactionn.model.PaymentTransaction;
import org.springframework.stereotype.Service;

@Service
public interface PaymentTransactionService {
    public PaymentResponseDto sendRequestPaymentService(PaymentAmountDto paymentAmountDto);

    public void delete(PaymentTransaction paymentTransaction);

    public PaymentTransaction save(PaymentTransaction paymentTransaction);

}
