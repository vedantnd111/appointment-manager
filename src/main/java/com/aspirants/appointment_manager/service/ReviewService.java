package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.ReviewRequest;
import com.aspirants.appointment_manager.dto.ReviewResponse;
import com.aspirants.appointment_manager.entity.Appointment;
import com.aspirants.appointment_manager.entity.Review;
import com.aspirants.appointment_manager.entity.Store;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.enums.AppointmentStatus;
import com.aspirants.appointment_manager.exception.InvalidRequestException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.AppointmentRepository;
import com.aspirants.appointment_manager.repository.ReviewRepository;
import com.aspirants.appointment_manager.repository.StoreRepository;
import com.aspirants.appointment_manager.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserProfileRepository userRepository;
    private final StoreRepository storeRepository;
    private final AppointmentRepository appointmentRepository;

    public ReviewService(ReviewRepository reviewRepository,
            UserProfileRepository userRepository,
            StoreRepository storeRepository,
            AppointmentRepository appointmentRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public ReviewResponse createReview(ReviewRequest request) {
        UserProfile user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", request.getStoreId()));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", request.getAppointmentId()));

        // Validate appointment belongs to user and store
        if (!appointment.getUser().getUserId().equals(user.getUserId()) ||
                !appointment.getStore().getStoreId().equals(store.getStoreId())) {
            throw new InvalidRequestException("Appointment does not match user and store");
        }

        // Validate appointment is completed
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new InvalidRequestException("Only completed appointments can be reviewed");
        }

        Review review = new Review();
        review.setUser(user);
        review.setStore(store);
        review.setAppointment(appointment);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        // Update store average rating
        updateStoreRating(store);

        return mapToResponse(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        return mapToResponse(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeId));

        return reviewRepository.findByStoreOrderByCreatedAtDesc(store).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByUser(Long userId) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return reviewRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ReviewResponse updateReview(Long id, ReviewRequest request) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));

        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review updatedReview = reviewRepository.save(review);

        // Update store average rating
        updateStoreRating(review.getStore());

        return mapToResponse(updatedReview);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));

        Store store = review.getStore();
        reviewRepository.delete(review);

        // Update store average rating
        updateStoreRating(store);
    }

    private void updateStoreRating(Store store) {
        BigDecimal averageRating = reviewRepository.calculateAverageRating(store);
        if (averageRating == null) {
            averageRating = BigDecimal.ZERO;
        }
        store.setAverageRating(averageRating);
        storeRepository.save(store);
    }

    private ReviewResponse mapToResponse(Review review) {
        return new ReviewResponse(
                review.getReviewId(),
                review.getUser().getUserId(),
                review.getUser().getFirstName() + " " + review.getUser().getLastName(),
                review.getStore().getStoreId(),
                review.getStore().getStoreName(),
                review.getAppointment().getAppointmentId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt());
    }
}
