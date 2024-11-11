package com.project.appoinmentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.appoinmentservice.dto.AppointmentRequestDTO;
import com.project.appoinmentservice.dto.PaymentRequest;
import com.project.appoinmentservice.model.Appointment;
import com.project.appoinmentservice.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody AppointmentRequestDTO requestData) {
        ObjectMapper mapper = new ObjectMapper();
        Appointment appointment = mapper.convertValue(requestData, Appointment.class);
        PaymentRequest paymentRequest = mapper.convertValue(requestData, PaymentRequest.class);
        Map<String, Object> response = new HashMap<>();
        try{
            appointment.setPatientId(appointmentService.getPatientFromApi(requestData.getPatientEmail(), requestData.getPatientPhone(), requestData.getPatientName()));
            paymentRequest.setPatientId(appointmentService.getPatientFromApi(requestData.getPatientEmail(), requestData.getPatientPhone(), requestData.getPatientName()));
            paymentRequest.setAppointmentId(appointmentService.saveAppointment(appointment).getAppointmentId());
            boolean paymentSuccess = appointmentService.createPayment(paymentRequest);
            System.out.println(paymentSuccess);
            Appointment savedAppointment = appointmentService.saveAppointment(appointment);
            response.put("appointment", savedAppointment);
            response.put("paymentSuccess", paymentSuccess);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("appointment", null);
            response.put("paymentSuccess", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);        }
    }
}
