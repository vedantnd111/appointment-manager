package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Appointment;
import com.aspirants.appointment_manager.entity.Payment;
import com.aspirants.appointment_manager.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByAppointment(Appointment appointment);

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByPaymentStatus(PaymentStatus paymentStatus);
}
