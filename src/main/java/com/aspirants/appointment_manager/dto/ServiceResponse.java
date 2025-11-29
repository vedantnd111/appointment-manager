package com.aspirants.appointment_manager.dto;

import java.math.BigDecimal;

public class ServiceResponse {

    private Long serviceId;
    private CategoryResponse category;
    private Long vendorId;
    private String vendorName;
    private String serviceName;
    private String description;
    private Integer duration;
    private BigDecimal price;
    private Boolean isActive;

    // Constructors
    public ServiceResponse() {
    }

    public ServiceResponse(Long serviceId, CategoryResponse category, Long vendorId, String vendorName,
            String serviceName, String description, Integer duration, BigDecimal price, Boolean isActive) {
        this.serviceId = serviceId;
        this.category = category;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.serviceName = serviceName;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public void setCategory(CategoryResponse category) {
        this.category = category;
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

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
