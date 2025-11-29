package com.aspirants.appointment_manager.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;

    @NotNull(message = "Service ID is required")
    private Long serviceId;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @Size(max = 1000, message = "Notes must not exceed 1000 characters")
    private String notes;

    // Constructors
    public AppointmentRequest() {
    }

    public AppointmentRequest(Long userId, Long vendorId, Long serviceId, LocalDate appointmentDate,
            LocalTime startTime, String notes) {
        this.userId = userId;
        this.vendorId = vendorId;
        this.serviceId = serviceId;
        this.appointmentDate = appointmentDate;
        this.startTime = startTime;
        this.notes = notes;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
