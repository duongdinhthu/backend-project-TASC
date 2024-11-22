package com.project.appoinmentservice.service.implement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.appoinmentservice.dto.*;
import com.project.appoinmentservice.model.Appointment;
import com.project.appoinmentservice.model.AppointmentStatus;
import com.project.appoinmentservice.repository.AppointmentRepository;
import com.project.appoinmentservice.service.AppointmentService;
import com.project.appoinmentservice.service.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Iterator;
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


    public ResponseEntity<TransactionResponseDto> transactionSuccess(AppoinmntTransactionDTO appoinmntTransactionDTO) {
        System.out.println("Gọi sang transactionService thông báo success");
        System.out.println(appoinmntTransactionDTO);
        String url = "http://localhost:8080/api/transactions/create";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AppoinmntTransactionDTO> entity = new HttpEntity<>(appoinmntTransactionDTO, headers);

        try {
            // Send the request to the transaction service and receive a response
            ResponseEntity<TransactionResponseDto> response = restTemplate.exchange(url, HttpMethod.POST, entity, TransactionResponseDto.class);

            System.out.println("API Response Status: " + response.getStatusCode());
            System.out.println("API Response Body: " + response.getBody());

            // Handle the response status and return an appropriate response to the client
            if (response.getStatusCode() == HttpStatus.CREATED && response.getBody() != null) {
                // Return the response with the orderID and approvalLink
                return ResponseEntity.status(HttpStatus.CREATED).body(response.getBody());
            } else {
                // If the response status is not successful or body is null, return a failure response
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionResponseDto());
            }
        } catch (HttpClientErrorException e) {
            // Handle client-side errors (4xx)
            System.err.println("Lỗi từ phía client: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionResponseDto());
        } catch (HttpServerErrorException e) {
            // Handle server-side errors (5xx)
            System.err.println("Lỗi từ phía server: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TransactionResponseDto());
        } catch (ResourceAccessException e) {
            // Handle connection issues (e.g., unable to connect to the service)
            System.err.println("Lỗi kết nối: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new TransactionResponseDto());
        } catch (Exception e) {
            // Handle any other unexpected errors
            System.err.println("Lỗi không xác định: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TransactionResponseDto());
        }
    }



    @Override
    public List<Appointment> getAppointmentsByDoctor(Integer doctorId) {
        List<Appointment> appointments = appointmentRepository.findByDoctorId(doctorId);
        Iterator<Appointment> iterator = appointments.iterator();

        LocalDate currentDate = LocalDate.now(); // Lấy ngày hiện tại

        while (iterator.hasNext()) {
            Appointment appointment = iterator.next();

            // Chuyển đổi medicalDay từ Date sang LocalDate
            LocalDate medicalDay = appointment.getMedicalDay().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            // Kiểm tra điều kiện: loại bỏ các lịch hẹn đã xác nhận và có ngày trong quá khứ
            // Lịch hẹn được giữ lại nếu ngày medicalDay là hôm nay hoặc trong tương lai
            if (appointment.getStatus() != AppointmentStatus.CONFIRMED || medicalDay.isBefore(currentDate)) {
                iterator.remove(); // Loại bỏ phần tử nếu không thỏa mãn điều kiện
            }
        }
        return appointments;
    }



    @Override
    public ResponseEntity<TransactionResponseDto> register(AppointmentRequestDTO appointmentRequestDTO) {
        System.out.println("dữ liệu đã nhận !");
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Appointment appointment = mapper.convertValue(appointmentRequestDTO, Appointment.class);
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        PaymentRequest paymentRequest = mapper.convertValue(appointmentRequestDTO, PaymentRequest.class);
        System.out.println(paymentRequest.toString());
        Map<String, Object> response = new HashMap<>();
        try {
            appointment.setPatientId(getPatientFromApi(appointmentRequestDTO.getPatientEmail(), appointmentRequestDTO.getPatientPhone(), appointmentRequestDTO.getPatientName()));
            paymentRequest.setPatientId(getPatientFromApi(appointmentRequestDTO.getPatientEmail(), appointmentRequestDTO.getPatientPhone(), appointmentRequestDTO.getPatientName()));
            paymentRequest.setAppointmentId(saveAppointment(appointment).getAppointmentId());
//            System.out.println(paymentSuccess);
//            if (paymentSuccess) {
//                sendEmail.sendEmailFormRegisterAppointment();
//            }
            Appointment savedAppointment = saveAppointment(appointment);
            if (savedAppointment != null) { // Kiểm tra nếu lưu thành công
                AppoinmntTransactionDTO appointmentTransactionDTO = new AppoinmntTransactionDTO();
                appointmentTransactionDTO.setAppointmentId(savedAppointment.getAppointmentId());
                appointmentTransactionDTO.setRandomCode(appointmentRequestDTO.getRandomCode());
                appointmentTransactionDTO.setStatus("pending");
                appointmentTransactionDTO.setPaymentAmount(appointmentRequestDTO.getPaymentAmount());
                ResponseEntity<TransactionResponseDto> transactionSuccess = transactionSuccess(appointmentTransactionDTO);
                if (transactionSuccess!= null){
                    response.put("success", true);
                    sendEmail.sendEmailFormRegisterAppointment(savedAppointment.getMedicalDay().toString(),savedAppointment.getPatientEmail(),savedAppointment.getSlot().toString());
                    return transactionSuccess;
                }else {
                    response.put("success", false);
                    System.out.println("không thành công , call back !");
                    appointmentRepository.delete(savedAppointment);
                }
            } else {
                appointmentRepository.delete(savedAppointment);
                System.out.println("không thành công , call back !");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new TransactionResponseDto());
            }
            response.put("appointment", savedAppointment);
            return null;
        } catch (Exception e) {
            response.put("appointment", null);
            response.put("paymentSuccess", false);
            return null;
        }
    }


}
