package com.aspirants.appointment_manager.controller;

import com.aspirants.appointment_manager.dto.AppointmentRequest;
import com.aspirants.appointment_manager.dto.AppointmentResponse;
import com.aspirants.appointment_manager.enums.AppointmentStatus;
import com.aspirants.appointment_manager.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> createAppointment(@Valid @RequestBody AppointmentRequest request) {
        AppointmentResponse response = appointmentService.createAppointment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentById(@PathVariable Long id) {
        AppointmentResponse response = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByUser(@PathVariable Long userId) {
        List<AppointmentResponse> responses = appointmentService.getAppointmentsByUser(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<AppointmentResponse>> getAppointmentsByVendor(@PathVariable Long vendorId) {
        List<AppointmentResponse> responses = appointmentService.getAppointmentsByVendor(vendorId);
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AppointmentResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam AppointmentStatus status) {
        AppointmentResponse response = appointmentService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
