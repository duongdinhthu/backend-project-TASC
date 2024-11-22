package com.project.transactionn.controller;

import com.netflix.discovery.converters.Auto;
import com.project.transactionn.dto.*;

import com.project.transactionn.model.AppointmentTransaction;
import com.project.transactionn.model.PaymentTransaction;
import com.project.transactionn.model.Transaction;
import com.project.transactionn.service.AppointmntTransactionService;
import com.project.transactionn.service.NotificationService;
import com.project.transactionn.service.PaymentTransactionService;
import com.project.transactionn.service.TransactionService;
import jakarta.ws.rs.POST;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AppointmntTransactionService appointmntTransactionService;

    @Autowired
    PaymentTransactionService paymentTransactionService;

    @Autowired
    NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDto> createTransaction(@RequestBody AppointmentRequest appointmentRequest) {
        try {
            System.out.println(appointmentRequest.toString());

            // Save the transaction
            Transaction transaction = new Transaction();
            transactionService.saveTransaction(transaction);

            // Create AppointmentTransaction and link it to the transaction
            AppointmentTransaction appointmentTransaction = new AppointmentTransaction();
            appointmentTransaction.setAppointmentId(appointmentRequest.getAppointmentId());
            appointmentTransaction.setStatus(appointmentRequest.getStatus());
            appointmentTransaction.setTransaction(transaction);

            // Create the PaymentAmountDto based on the incoming appointmentRequest
            PaymentAmountDto paymentAmountDto = new PaymentAmountDto();
            paymentAmountDto.setPaymentAmount(appointmentRequest.getPaymentAmount());

            // Call the service to send the payment request and receive the payment response
            PaymentResponseDto paymentResponseDto = paymentTransactionService.sendRequestPaymentService(paymentAmountDto);

            if (paymentResponseDto != null) {
                transaction.setStatus("CONFIRMED");
                transactionService.saveTransaction(transaction);
                return ResponseEntity.status(HttpStatus.CREATED).body(paymentResponseDto);
            } else {
                // Handle failed payment case (if no valid response)
                System.err.println("Payment failed, no response from payment service");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PaymentResponseDto());
            }

        } catch (Exception e) {
            System.err.println("Error in createTransaction: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new PaymentResponseDto());
        }
    }


}
