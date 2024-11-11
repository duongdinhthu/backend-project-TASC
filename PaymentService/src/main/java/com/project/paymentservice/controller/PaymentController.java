package com.project.paymentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.paymentservice.dto.PaymentDTO;
import com.project.paymentservice.model.Payment;
import com.project.paymentservice.model.PaymentStatus;
import com.project.paymentservice.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/paymentservice")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Create Payment
    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO paymentDTO) {
        System.out.println(paymentDTO.toString());
        ObjectMapper mapper = new ObjectMapper();
        Payment createdPayment = mapper.convertValue(paymentDTO, Payment.class);
        createdPayment.setPaymentStatus(PaymentStatus.PENDING); // Giả sử status mặc định là PENDING
        createdPayment.setCreatedAt(LocalDateTime.now()); // Thời gian tạo
        createdPayment.setUpdatedAt(LocalDateTime.now()); // Thời gian cập nhật
        Payment savedPayment = paymentService.createPayment(createdPayment);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }


    // Get All Payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    // Get Payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);

        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Update Payment
    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Integer id, @RequestBody Payment paymentDetails) {
        Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);

        if (updatedPayment != null) {
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete Payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        boolean isDeleted = paymentService.deletePayment(id);

        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
