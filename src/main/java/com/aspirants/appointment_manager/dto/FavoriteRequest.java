package com.aspirants.appointment_manager.dto;

import jakarta.validation.constraints.NotNull;

public class FavoriteRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;

    // Constructors
    public FavoriteRequest() {
    }

    public FavoriteRequest(Long userId, Long vendorId) {
        this.userId = userId;
        this.vendorId = vendorId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
}
