package com.project.appoinmentservice.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.appoinmentservice.dto.AppointmentRequestDTO;
import com.project.appoinmentservice.dto.PatientRequest;
import com.project.appoinmentservice.dto.PaymentRequest;
import com.project.appoinmentservice.model.Appointment;
import com.project.appoinmentservice.repository.AppointmentRepository;
import com.project.appoinmentservice.service.AppointmentService;
import com.project.appoinmentservice.service.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private final RestTemplate restTemplate  = new RestTemplate();

    @Autowired
    private SendEmail sendEmail;

    public Integer getPatientFromApi(String patientEmail, String patientPhone, String patientName) {
        String url = "http://localhost:8080/api/userservice/notjwt/patients/check";
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
            return (Integer)responseBody.get("id");
        } else {
            // Xử lý trường hợp không tìm thấy patientId
            throw new RuntimeException("Không tìm thấy patientId trong phản hồi");
        }
    }

    public Appointment saveAppointment(Appointment appointment) {
           return appointmentRepository.save(appointment);
    }
    public boolean createPayment(PaymentRequest paymentDTO) {
        System.out.println(paymentDTO.toString());
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

    @Override
    public List<Appointment> getAppointmentsByDoctor(Integer doctorId) {
        return appointmentRepository.findByDoctorId(doctorId);
    }

    @Override
    public ResponseEntity<Map<String, Object>> register(AppointmentRequestDTO appointmentRequestDTO) {
        System.out.println("gọi hàm register");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Appointment appointment = mapper.convertValue(appointmentRequestDTO, Appointment.class);
        appointment.setStatus("success");
        PaymentRequest paymentRequest = mapper.convertValue(appointmentRequestDTO, PaymentRequest.class);
        paymentRequest.setPaymentCurrency("USD");
        paymentRequest.setPaymentAmount(appointment.getPrice());
        System.out.println(paymentRequest.toString());
        Map<String, Object> response = new HashMap<>();
        try {
            appointment.setPatientId(getPatientFromApi(appointmentRequestDTO.getPatientEmail(), appointmentRequestDTO.getPatientPhone(), appointmentRequestDTO.getPatientName()));
            paymentRequest.setPatientId(getPatientFromApi(appointmentRequestDTO.getPatientEmail(), appointmentRequestDTO.getPatientPhone(), appointmentRequestDTO.getPatientName()));
            paymentRequest.setAppointmentId(saveAppointment(appointment).getAppointmentId());
            boolean paymentSuccess = createPayment(paymentRequest);
            System.out.println(paymentSuccess);
//            if (paymentSuccess) {
//                sendEmail.sendEmailFormRegisterAppointment();
//            }
            Appointment savedAppointment = saveAppointment(appointment);
            response.put("appointment", savedAppointment);
            response.put("paymentSuccess", paymentSuccess);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("appointment", null);
            response.put("paymentSuccess", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


}
