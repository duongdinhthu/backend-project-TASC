package com.project.paymentservice.controller;

import com.project.paymentservice.dto.PaymentAmountDto;
import com.project.paymentservice.dto.PaymentResponseDto;
import com.project.paymentservice.model.Payment;
import com.project.paymentservice.service.PaymentService;
import com.project.paymentservice.service.PaypalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/paymentservice")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaypalService paypalService;
    // Create Payment
    @PostMapping("/create")
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentAmountDto paymentAmountDto) {
        System.out.println("Dữ liệu đã nhận :" + paymentAmountDto.toString());

        // Gọi service để tạo đơn hàng và nhận phản hồi từ PayPal
        PaymentResponseDto response = paypalService.createPayment(paymentAmountDto);

        System.out.println("Dữ liệu đã thêm :" + response.toString());

        // Trả về phản hồi với mã trạng thái CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
