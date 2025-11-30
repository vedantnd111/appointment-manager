package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.PaymentRequest;
import com.aspirants.appointment_manager.dto.PaymentResponse;
import com.aspirants.appointment_manager.entity.Appointment;
import com.aspirants.appointment_manager.entity.Payment;
import com.aspirants.appointment_manager.enums.PaymentStatus;
import com.aspirants.appointment_manager.exception.DuplicateResourceException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.AppointmentRepository;
import com.aspirants.appointment_manager.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;

    public PaymentService(PaymentRepository paymentRepository, AppointmentRepository appointmentRepository) {
        this.paymentRepository = paymentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public PaymentResponse createPayment(PaymentRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", request.getAppointmentId()));

        // Check if payment already exists for this appointment
        if (paymentRepository.findByAppointment(appointment).isPresent()) {
            throw new DuplicateResourceException("Payment", "appointmentId", request.getAppointmentId());
        }

        Payment payment = new Payment();
        payment.setAppointment(appointment);
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setTransactionId(request.getTransactionId());

        Payment savedPayment = paymentRepository.save(payment);
        return mapToResponse(savedPayment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));
        return mapToResponse(payment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByAppointmentId(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        Payment payment = paymentRepository.findByAppointment(appointment)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "appointmentId", appointmentId));

        return mapToResponse(payment);
    }

    public PaymentResponse updateStatus(Long id, PaymentStatus status) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "id", id));

        payment.setPaymentStatus(status);
        Payment updatedPayment = paymentRepository.save(payment);
        return mapToResponse(updatedPayment);
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return new PaymentResponse(
                payment.getPaymentId(),
                payment.getAppointment().getAppointmentId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentStatus(),
                payment.getTransactionId(),
                payment.getCreatedAt());
    }
}
