package com.aspirants.appointment_manager.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public class StoreRequest {

    @NotNull(message = "Vendor ID is required")
    private Long vendorId;

    @NotBlank(message = "Store name is required")
    @Size(max = 100, message = "Store name must not exceed 100 characters")
    private String storeName;

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

    // Constructors
    public StoreRequest() {
    }

    public StoreRequest(Long vendorId, String storeName, String emailId, String phoneNo,
            String description, AddressDTO address) {
        this.vendorId = vendorId;
        this.storeName = storeName;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.description = description;
        this.address = address;
    }

    // Getters and Setters
    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
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
}
