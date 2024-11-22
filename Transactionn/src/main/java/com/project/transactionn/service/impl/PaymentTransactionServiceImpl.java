package com.project.transactionn.service.impl;

import com.project.transactionn.dto.PaymentAmountDto;

import com.project.transactionn.dto.PaymentResponseDto;
import com.project.transactionn.model.PaymentTransaction;
import com.project.transactionn.model.Transaction;
import com.project.transactionn.repository.PaymentTransactionRepository;
import com.project.transactionn.service.PaymentTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentTransactionServiceImpl implements PaymentTransactionService {
    @Autowired
    PaymentTransactionRepository paymentTransactionRepository;

    private final RestTemplate restTemplate  = new RestTemplate();

    @Override
    public PaymentResponseDto sendRequestPaymentService(PaymentAmountDto paymentAmountDto) {
        System.out.println("Gọi sang payment service...");
        String url = "http://localhost:8080/api/paymentservice/create";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentAmountDto> entity = new HttpEntity<>(paymentAmountDto, headers);

        try {
            // Gửi request và nhận phản hồi
            ResponseEntity<PaymentResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, PaymentResponseDto.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                PaymentResponseDto responseBody = response.getBody();
                // Xử lý dữ liệu từ API trả về nếu cần
                System.out.println("Thanh toán đã thành công với OrderID: " + responseBody.getOrderID());
                System.out.println("Liên kết thanh toán: " + responseBody.getApprovalLink());
                return responseBody; // Trả về đối tượng PaymentResponseDto
            } else {
                System.err.println("Thanh toán thất bại với status: " + response.getStatusCode());
                return null;
            }
        } catch (Exception e) {
            // Logging lỗi
            System.err.println("Lỗi khi gọi API PaymentService: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void delete(PaymentTransaction paymentTransaction){
        paymentTransactionRepository.delete(paymentTransaction);
    }
    @Override
    public PaymentTransaction save(PaymentTransaction paymentTransaction){
        return paymentTransactionRepository.save(paymentTransaction);
    }

}
