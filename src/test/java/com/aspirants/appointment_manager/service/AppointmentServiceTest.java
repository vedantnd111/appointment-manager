package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.AppointmentRequest;
import com.aspirants.appointment_manager.dto.AppointmentResponse;
import com.aspirants.appointment_manager.entity.*;
import com.aspirants.appointment_manager.enums.AppointmentStatus;
import com.aspirants.appointment_manager.exception.AppointmentConflictException;
import com.aspirants.appointment_manager.exception.InvalidRequestException;
import com.aspirants.appointment_manager.repository.AppointmentRepository;
import com.aspirants.appointment_manager.repository.ServiceRepository;
import com.aspirants.appointment_manager.repository.StoreRepository;
import com.aspirants.appointment_manager.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserProfileRepository userRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private UserProfile user;
    private Store store;
    private Service service;
    private Category category;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        user = new UserProfile();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        store = new Store();
        store.setStoreId(1L);
        store.setStoreName("Test Store");

        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        service = new Service();
        service.setServiceId(1L);
        service.setServiceName("Test Service");
        service.setDuration(60);
        service.setPrice(java.math.BigDecimal.valueOf(100));
        service.setStore(store);
        service.setCategory(category);

        appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setUser(user);
        appointment.setStore(store);
        appointment.setService(service);
        appointment.setAppointmentDate(LocalDate.now().plusDays(1));
        appointment.setStartTime(LocalTime.of(10, 0));
        appointment.setEndTime(LocalTime.of(11, 0));
        appointment.setStatus(AppointmentStatus.PENDING);
    }

    @Test
    void createAppointment_Success() {
        AppointmentRequest request = new AppointmentRequest();
        request.setUserId(1L);
        request.setStoreId(1L);
        request.setServiceId(1L);
        request.setAppointmentDate(LocalDate.now().plusDays(1));
        request.setStartTime(LocalTime.of(10, 0));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
        when(appointmentRepository.findConflictingAppointments(any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        AppointmentResponse response = appointmentService.createAppointment(request);

        assertNotNull(response);
        assertEquals(AppointmentStatus.PENDING, response.getStatus());
        assertEquals(store.getStoreName(), response.getStoreName());
        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void createAppointment_StoreMismatch_ThrowsException() {
        Store otherStore = new Store();
        otherStore.setStoreId(2L); // Different ID

        AppointmentRequest request = new AppointmentRequest();
        request.setUserId(1L);
        request.setStoreId(2L); // Requesting different store
        request.setServiceId(1L); // Service belongs to store 1

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storeRepository.findById(2L)).thenReturn(Optional.of(otherStore));
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        assertThrows(InvalidRequestException.class, () -> appointmentService.createAppointment(request));
    }

    @Test
    void createAppointment_Conflict_ThrowsException() {
        AppointmentRequest request = new AppointmentRequest();
        request.setUserId(1L);
        request.setStoreId(1L);
        request.setServiceId(1L);
        request.setAppointmentDate(LocalDate.now().plusDays(1));
        request.setStartTime(LocalTime.of(10, 0));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

        List<Appointment> conflicts = Collections.singletonList(new Appointment());
        when(appointmentRepository.findConflictingAppointments(any(), any(), any(), any())).thenReturn(conflicts);

        assertThrows(AppointmentConflictException.class, () -> appointmentService.createAppointment(request));
    }

    @Test
    void getAppointmentsByStore_Success() {
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(appointmentRepository.findByStore(store)).thenReturn(Collections.singletonList(appointment));

        List<AppointmentResponse> responses = appointmentService.getAppointmentsByStore(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(store.getStoreName(), responses.get(0).getStoreName());
    }
}
