package com.aspirants.appointment_manager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class VendorProfileRequest {

    @NotBlank(message = "Vendor name is required")
    @Size(max = 100, message = "Vendor name must not exceed 100 characters")
    private String vendorName;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String emailId;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNo;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    @Valid
    @NotNull(message = "Address is required")
    private AddressDTO address;

    @NotBlank(message = "GST number is required")
    @Size(max = 20, message = "GST number must not exceed 20 characters")
    private String gstNumber;

    // Constructors
    public VendorProfileRequest() {
    }

    public VendorProfileRequest(String vendorName, Long categoryId, String emailId, String phoneNo,
            String description, AddressDTO address, String gstNumber) {
        this.vendorName = vendorName;
        this.categoryId = categoryId;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.description = description;
        this.address = address;
        this.gstNumber = gstNumber;
    }

    // Getters and Setters
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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
}
