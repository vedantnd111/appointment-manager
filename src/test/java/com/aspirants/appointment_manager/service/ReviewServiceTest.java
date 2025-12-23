package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.ReviewRequest;
import com.aspirants.appointment_manager.dto.ReviewResponse;
import com.aspirants.appointment_manager.entity.*;
import com.aspirants.appointment_manager.enums.AppointmentStatus;
import com.aspirants.appointment_manager.exception.InvalidRequestException;
import com.aspirants.appointment_manager.repository.AppointmentRepository;
import com.aspirants.appointment_manager.repository.ReviewRepository;
import com.aspirants.appointment_manager.repository.StoreRepository;
import com.aspirants.appointment_manager.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserProfileRepository userRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private ReviewService reviewService;

    private UserProfile user;
    private Store store;
    private Appointment appointment;
    private Review review;

    @BeforeEach
    void setUp() {
        user = new UserProfile();
        user.setUserId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");

        store = new Store();
        store.setStoreId(1L);
        store.setStoreName("Test Store");

        appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setUser(user);
        appointment.setStore(store);
        appointment.setStatus(AppointmentStatus.COMPLETED);

        review = new Review();
        review.setReviewId(1L);
        review.setUser(user);
        review.setStore(store);
        review.setAppointment(appointment);
        review.setRating(5);
        review.setComment("Great service!");
        review.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void createReview_Success() {
        ReviewRequest request = new ReviewRequest();
        request.setUserId(1L);
        request.setStoreId(1L);
        request.setAppointmentId(1L);
        request.setRating(5);
        request.setComment("Great service!");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(reviewRepository.calculateAverageRating(store)).thenReturn(BigDecimal.valueOf(5.0));

        ReviewResponse response = reviewService.createReview(request);

        assertNotNull(response);
        assertEquals(5, response.getRating());
        verify(reviewRepository).save(any(Review.class));
        verify(storeRepository).save(store); // Should update store rating
    }

    @Test
    void createReview_AppointmentNotCompleted_ThrowsException() {
        appointment.setStatus(AppointmentStatus.PENDING);

        ReviewRequest request = new ReviewRequest();
        request.setUserId(1L);
        request.setStoreId(1L);
        request.setAppointmentId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        assertThrows(InvalidRequestException.class, () -> reviewService.createReview(request));
    }

    @Test
    void getReviewsByStore_Success() {
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(reviewRepository.findByStoreOrderByCreatedAtDesc(store)).thenReturn(Collections.singletonList(review));

        List<ReviewResponse> responses = reviewService.getReviewsByStore(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(store.getStoreName(), responses.get(0).getStoreName());
    }
}
