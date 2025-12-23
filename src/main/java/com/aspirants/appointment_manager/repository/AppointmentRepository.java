package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Appointment;
import com.aspirants.appointment_manager.entity.Store;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

        List<Appointment> findByUser(UserProfile user);

        List<Appointment> findByStore(Store store);

        List<Appointment> findByStatus(AppointmentStatus status);

        List<Appointment> findByAppointmentDate(LocalDate date);

        List<Appointment> findByUserAndStatus(UserProfile user, AppointmentStatus status);

        List<Appointment> findByStoreAndStatus(Store store, AppointmentStatus status);

        List<Appointment> findByStoreAndAppointmentDate(Store store, LocalDate date);

        @Query("SELECT a FROM Appointment a WHERE a.store = :store AND a.appointmentDate = :date " +
                        "AND a.status NOT IN ('CANCELLED') " +
                        "AND ((a.startTime < :endTime AND a.endTime > :startTime))")
        List<Appointment> findConflictingAppointments(
                        @Param("store") Store store,
                        @Param("date") LocalDate date,
                        @Param("startTime") LocalTime startTime,
                        @Param("endTime") LocalTime endTime);
}
