package com.project.appoinmentservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")  // Chỉ định tên cột trong cơ sở dữ liệu nếu cần
    private Integer appointmentId;

    @Column(name = "patient_id")
    private String patientId;

    @Column(name = "doctor_id")
    private String doctorId;

    @Column(name = "staff_id")
    private String staffId;

    @Column(name = "appointment_date")
    private Date appointmentDate;

    @Column(name = "medical_day")
    private Date medicalDay;

    @Column(name = "slot")
    private Integer slot;

    @Column(name = "status")
    private String status;

    @Column(name = "price", precision = 18, scale = 2)  // precision: tổng số chữ số, scale: số chữ số sau dấu thập phân
    private BigDecimal price;

    @Column(name = "patientEmail")  // precision: tổng số chữ số, scale: số chữ số sau dấu thập phân
    private String patientEmail;

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Date getMedicalDay() {
        return medicalDay;
    }

    public void setMedicalDay(Date medicalDay) {
        this.medicalDay = medicalDay;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getSlot() {
        return slot;
    }

    public void setSlot(Integer slot) {
        this.slot = slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Phương thức được gọi trước khi lưu bản ghi mới vào cơ sở dữ liệu
    @PrePersist
    public void prePersist() {
        if (this.appointmentDate == null) {
            this.appointmentDate = new Date();
        }
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patientId='" + patientId + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", medicalDay=" + medicalDay +
                ", slot=" + slot +
                ", status='" + status + '\'' +
                ", price=" + price +
                ", patientEmail='" + patientEmail + '\'' +
                '}';
    }
}
