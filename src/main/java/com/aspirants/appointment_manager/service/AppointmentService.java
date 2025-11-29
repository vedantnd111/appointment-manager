package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.AppointmentRequest;
import com.aspirants.appointment_manager.dto.AppointmentResponse;
import com.aspirants.appointment_manager.dto.CategoryResponse;
import com.aspirants.appointment_manager.dto.ServiceResponse;
import com.aspirants.appointment_manager.entity.Appointment;
import com.aspirants.appointment_manager.entity.Service;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.entity.VendorProfile;
import com.aspirants.appointment_manager.enums.AppointmentStatus;
import com.aspirants.appointment_manager.exception.AppointmentConflictException;
import com.aspirants.appointment_manager.exception.InvalidRequestException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.AppointmentRepository;
import com.aspirants.appointment_manager.repository.ServiceRepository;
import com.aspirants.appointment_manager.repository.UserProfileRepository;
import com.aspirants.appointment_manager.repository.VendorProfileRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserProfileRepository userRepository;
    private final VendorProfileRepository vendorRepository;
    private final ServiceRepository serviceRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
            UserProfileRepository userRepository,
            VendorProfileRepository vendorRepository,
            ServiceRepository serviceRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
        this.serviceRepository = serviceRepository;
    }

    public AppointmentResponse createAppointment(AppointmentRequest request) {
        UserProfile user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        VendorProfile vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", request.getVendorId()));

        Service service = serviceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", request.getServiceId()));

        // Validate vendor matches service
        if (!service.getVendor().getVendorId().equals(vendor.getVendorId())) {
            throw new InvalidRequestException("Service does not belong to the specified vendor");
        }

        // Calculate end time
        LocalTime endTime = request.getStartTime().plusMinutes(service.getDuration());

        // Check for conflicts
        List<Appointment> conflicts = appointmentRepository.findConflictingAppointments(
                vendor, request.getAppointmentDate(), request.getStartTime(), endTime);

        if (!conflicts.isEmpty()) {
            throw new AppointmentConflictException("The selected time slot is already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setVendor(vendor);
        appointment.setService(service);
        appointment.setAppointmentDate(request.getAppointmentDate());
        appointment.setStartTime(request.getStartTime());
        appointment.setEndTime(endTime);
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setNotes(request.getNotes());

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToResponse(savedAppointment);
    }

    @Transactional(readOnly = true)
    public AppointmentResponse getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        return mapToResponse(appointment);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByUser(Long userId) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return appointmentRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> getAppointmentsByVendor(Long vendorId) {
        VendorProfile vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", vendorId));

        return appointmentRepository.findByVendor(vendor).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public AppointmentResponse updateStatus(Long id, AppointmentStatus status) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        appointment.setStatus(status);
        Appointment updatedAppointment = appointmentRepository.save(appointment);
        return mapToResponse(updatedAppointment);
    }

    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new InvalidRequestException("Cannot cancel a completed appointment");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        CategoryResponse categoryResponse = new CategoryResponse(
                appointment.getService().getCategory().getCategoryId(),
                appointment.getService().getCategory().getCategoryName(),
                appointment.getService().getCategory().getDescription(),
                appointment.getService().getCategory().getIconUrl(),
                appointment.getService().getCategory().getIsActive());

        ServiceResponse serviceResponse = new ServiceResponse(
                appointment.getService().getServiceId(),
                categoryResponse,
                appointment.getVendor().getVendorId(),
                appointment.getVendor().getVendorName(),
                appointment.getService().getServiceName(),
                appointment.getService().getDescription(),
                appointment.getService().getDuration(),
                appointment.getService().getPrice(),
                appointment.getService().getIsActive());

        return new AppointmentResponse(
                appointment.getAppointmentId(),
                appointment.getUser().getUserId(),
                appointment.getUser().getFirstName() + " " + appointment.getUser().getLastName(),
                appointment.getVendor().getVendorId(),
                appointment.getVendor().getVendorName(),
                serviceResponse,
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getEndTime(),
                appointment.getStatus(),
                appointment.getNotes(),
                appointment.getCreatedAt(),
                appointment.getUpdatedAt());
    }
}
