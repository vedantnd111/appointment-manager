package com.aspirants.appointment_manager.controller;

import com.aspirants.appointment_manager.dto.PaymentRequest;
import com.aspirants.appointment_manager.dto.PaymentResponse;
import com.aspirants.appointment_manager.enums.PaymentStatus;
import com.aspirants.appointment_manager.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Long id) {
        PaymentResponse response = paymentService.getPaymentById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<PaymentResponse> getPaymentByAppointmentId(@PathVariable Long appointmentId) {
        PaymentResponse response = paymentService.getPaymentByAppointmentId(appointmentId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PaymentResponse> updateStatus(
            @PathVariable Long id,
            @RequestParam PaymentStatus status) {
        PaymentResponse response = paymentService.updateStatus(id, status);
        return ResponseEntity.ok(response);
    }
}
