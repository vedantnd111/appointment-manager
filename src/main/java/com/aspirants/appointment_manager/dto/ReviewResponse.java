package com.aspirants.appointment_manager.dto;

import java.time.LocalDateTime;

public class ReviewResponse {

    private Long reviewId;
    private Long userId;
    private String userName;
    private Long storeId;
    private String storeName;
    private Long appointmentId;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    // Constructors
    public ReviewResponse() {
    }

    public ReviewResponse(Long reviewId, Long userId, String userName, Long storeId, String storeName,
            Long appointmentId, Integer rating, String comment, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.userName = userName;
        this.storeId = storeId;
        this.storeName = storeName;
        this.appointmentId = appointmentId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
