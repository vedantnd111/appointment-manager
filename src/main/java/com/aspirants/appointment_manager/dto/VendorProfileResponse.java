package com.aspirants.appointment_manager.dto;

import java.math.BigDecimal;

public class VendorProfileResponse {

    private Long vendorId;
    private String vendorName;
    private CategoryResponse category;
    private String emailId;
    private String phoneNo;
    private String description;
    private AddressDTO address;
    private String gstNumber;
    private BigDecimal averageRating;
    private Boolean isActive;

    // Constructors
    public VendorProfileResponse() {
    }

    public VendorProfileResponse(Long vendorId, String vendorName, CategoryResponse category, String emailId,
            String phoneNo, String description, AddressDTO address, String gstNumber,
            BigDecimal averageRating, Boolean isActive) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.category = category;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.description = description;
        this.address = address;
        this.gstNumber = gstNumber;
        this.averageRating = averageRating;
        this.isActive = isActive;
    }

    // Getters and Setters
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

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
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

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
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
