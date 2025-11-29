package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.ReviewRequest;
import com.aspirants.appointment_manager.dto.ReviewResponse;
import com.aspirants.appointment_manager.entity.Appointment;
import com.aspirants.appointment_manager.entity.Review;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.entity.VendorProfile;
import com.aspirants.appointment_manager.enums.AppointmentStatus;
import com.aspirants.appointment_manager.exception.InvalidRequestException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.AppointmentRepository;
import com.aspirants.appointment_manager.repository.ReviewRepository;
import com.aspirants.appointment_manager.repository.UserProfileRepository;
import com.aspirants.appointment_manager.repository.VendorProfileRepository;
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
    private final VendorProfileRepository vendorRepository;
    private final AppointmentRepository appointmentRepository;

    public ReviewService(ReviewRepository reviewRepository,
            UserProfileRepository userRepository,
            VendorProfileRepository vendorRepository,
            AppointmentRepository appointmentRepository) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public ReviewResponse createReview(ReviewRequest request) {
        UserProfile user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        VendorProfile vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", request.getVendorId()));

        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", request.getAppointmentId()));

        // Validate appointment belongs to user and vendor
        if (!appointment.getUser().getUserId().equals(user.getUserId()) ||
                !appointment.getVendor().getVendorId().equals(vendor.getVendorId())) {
            throw new InvalidRequestException("Appointment does not match user and vendor");
        }

        // Validate appointment is completed
        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new InvalidRequestException("Only completed appointments can be reviewed");
        }

        Review review = new Review();
        review.setUser(user);
        review.setVendor(vendor);
        review.setAppointment(appointment);
        review.setRating(request.getRating());
        review.setComment(request.getComment());

        Review savedReview = reviewRepository.save(review);

        // Update vendor average rating
        updateVendorRating(vendor);

        return mapToResponse(savedReview);
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        return mapToResponse(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByVendor(Long vendorId) {
        VendorProfile vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", vendorId));

        return reviewRepository.findByVendorOrderByCreatedAtDesc(vendor).stream()
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

        // Update vendor average rating
        updateVendorRating(review.getVendor());

        return mapToResponse(updatedReview);
    }

    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));

        VendorProfile vendor = review.getVendor();
        reviewRepository.delete(review);

        // Update vendor average rating
        updateVendorRating(vendor);
    }

    private void updateVendorRating(VendorProfile vendor) {
        BigDecimal averageRating = reviewRepository.calculateAverageRating(vendor);
        if (averageRating == null) {
            averageRating = BigDecimal.ZERO;
        }
        vendor.setAverageRating(averageRating);
        vendorRepository.save(vendor);
    }

    private ReviewResponse mapToResponse(Review review) {
        return new ReviewResponse(
                review.getReviewId(),
                review.getUser().getUserId(),
                review.getUser().getFirstName() + " " + review.getUser().getLastName(),
                review.getVendor().getVendorId(),
                review.getVendor().getVendorName(),
                review.getAppointment().getAppointmentId(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt());
    }
}
