package com.aspirants.appointment_manager.dto;

import java.time.LocalDateTime;

public class FavoriteResponse {

    private Long favoriteId;
    private Long userId;
    private Long vendorId;
    private String vendorName;
    private String vendorIconUrl; // Assuming category icon or vendor image
    private LocalDateTime createdAt;

    // Constructors
    public FavoriteResponse() {
    }

    public FavoriteResponse(Long favoriteId, Long userId, Long vendorId, String vendorName,
            String vendorIconUrl, LocalDateTime createdAt) {
        this.favoriteId = favoriteId;
        this.userId = userId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.vendorIconUrl = vendorIconUrl;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Long getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(Long favoriteId) {
        this.favoriteId = favoriteId;
    }

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

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorIconUrl() {
        return vendorIconUrl;
    }

    public void setVendorIconUrl(String vendorIconUrl) {
        this.vendorIconUrl = vendorIconUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
