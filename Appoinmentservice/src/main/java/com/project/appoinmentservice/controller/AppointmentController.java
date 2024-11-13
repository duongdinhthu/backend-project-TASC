package com.project.appoinmentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.appoinmentservice.dto.AppointmentRequestDTO;
import com.project.appoinmentservice.dto.LockSlotDTO;
import com.project.appoinmentservice.dto.PaymentRequest;
import com.project.appoinmentservice.dto.ResponseDTO;
import com.project.appoinmentservice.model.Appointment;
import com.project.appoinmentservice.service.AppointmentService;
import com.project.appoinmentservice.service.LockSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private LockSlotService lockSlotService;

    @GetMapping("/checkslot/{doctorId}")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@PathVariable Integer doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        for (Appointment appointment : appointments) {
            System.out.println(appointments);
        }
        if (appointments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Trả về 204 nếu không có lịch hẹn nào
        }
        return ResponseEntity.ok(appointments); // Trả về 200 với danh sách các lịch hẹn
    }

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

    @PostMapping("/lock")
    public ResponseEntity<ResponseDTO> lockSlot(@RequestBody LockSlotDTO lockSlotDTO) {
        System.out.println(lockSlotDTO.toString());
        boolean isSlotLocked = lockSlotService.isSlotLocked(lockSlotDTO);  // Kiểm tra slot đã được khóa chưa
        boolean isCodeRandom = lockSlotService.checkIfCodeExists(lockSlotDTO.getRandomCode());  // Kiểm tra random code

        System.out.println("slot locked: " + isSlotLocked);
        System.out.println("isCodeRandom : " + isCodeRandom);

        // Chuyển điều kiện thành giá trị có thể so sánh với switch-case
        String caseSwitch = (isCodeRandom ? "RANDOM" : "NOT_RANDOM") + (isSlotLocked ? "_LOCKED" : "_UNLOCKED");

        switch (caseSwitch) {
            case "RANDOM_LOCKED":
                lockSlotService.updateKey(lockSlotDTO);
                System.out.println("Đã khóa slot mới, nhả slot cũ!");
                lockSlotService.getAllLockSlots();
                return ResponseEntity.ok(new ResponseDTO(200, "Đã khóa slot mới, nhả slot cũ!"));

            case "RANDOM_UNLOCKED":
                System.out.println("Slot này đã được khóa trước đó, không khóa lại!");
                lockSlotService.getAllLockSlots();
                return ResponseEntity.status(400).body(new ResponseDTO(400, "Slot này đã được khóa trước đó, không khóa lại!"));

            case "NOT_RANDOM_LOCKED":
                lockSlotService.lockSlot(lockSlotDTO);
                System.out.println("Slot đã được lock!");
                lockSlotService.getAllLockSlots();
                return ResponseEntity.ok(new ResponseDTO(200, "Slot đã được khóa!"));

            case "NOT_RANDOM_UNLOCKED":
                System.out.println("Slot này đã khóa, vui lòng chọn slot khác!");
                lockSlotService.getAllLockSlots();
                return ResponseEntity.status(409).body(new ResponseDTO(409, "Slot này đã khóa, vui lòng chọn slot khác!"));

            default:
                return ResponseEntity.status(500).body(new ResponseDTO(500, "Lỗi không xác định"));
        }
    }
}
