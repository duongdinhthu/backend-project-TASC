package com.project.appoinmentservice.service.implement;

import com.project.appoinmentservice.dto.PatientRequest;
import com.project.appoinmentservice.dto.PaymentRequest;
import com.project.appoinmentservice.model.Appointment;
import com.project.appoinmentservice.repository.AppointmentRepository;
import com.project.appoinmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private final RestTemplate restTemplate  = new RestTemplate();

    public String getPatientFromApi(String patientEmail, String patientPhone, String patientName) {
        String url = "http://localhost:8080/api/userservice/patients/check";
        PatientRequest requestBody = new PatientRequest(patientEmail, patientPhone, patientName);
        System.out.println(requestBody);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PatientRequest> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        System.out.println("Phản hồi API: " + response.getBody());
        // Kiểm tra phản hồi để lấy giá trị patientId
        Map<String, Object> responseBody = response.getBody();
        if (responseBody != null && responseBody.containsKey("id")) {
            // Lấy patientId dưới dạng String
            return responseBody.get("id").toString();
        } else {
            // Xử lý trường hợp không tìm thấy patientId
            throw new RuntimeException("Không tìm thấy patientId trong phản hồi");
        }
    }

    public Appointment saveAppointment(Appointment appointment) {
           return appointmentRepository.save(appointment);
    }
    public boolean createPayment(PaymentRequest paymentDTO) {
        String url = "http://localhost:8080/api/paymentservice/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentRequest> entity = new HttpEntity<>(paymentDTO, headers);
        try {
            ResponseEntity<Void> response = restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
            System.out.println("API Response Status: " + response.getStatusCode());
            if (response.getStatusCode() == HttpStatus.CREATED) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi API thanh toán: " + e.getMessage());
            return false;
        }
    }



}
