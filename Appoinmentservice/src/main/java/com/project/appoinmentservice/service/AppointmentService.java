package com.project.appoinmentservice.service;

import com.project.appoinmentservice.dto.PaymentRequest;
import com.project.appoinmentservice.model.Appointment;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {
    public String getPatientFromApi(String email, String phone, String name);
    public Appointment saveAppointment(Appointment appointment);
    public boolean createPayment(PaymentRequest paymentDTO);

}
