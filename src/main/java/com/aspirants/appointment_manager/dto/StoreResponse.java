package com.aspirants.appointment_manager.dto;

import java.math.BigDecimal;

public class StoreResponse {

    private Long storeId;
    private Long vendorId;
    private String vendorName;
    private String storeName;
    private String emailId;
    private String phoneNo;
    private String description;
    private AddressDTO address;
    private BigDecimal averageRating;
    private Boolean isActive;

    public StoreResponse() {
    }

    public StoreResponse(Long storeId, Long vendorId, String vendorName, String storeName,
            String emailId, String phoneNo, String description,
            AddressDTO address, BigDecimal averageRating, Boolean isActive) {
        this.storeId = storeId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.storeName = storeName;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.description = description;
        this.address = address;
        this.averageRating = averageRating;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
