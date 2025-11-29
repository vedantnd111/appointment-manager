package com.aspirants.appointment_manager.dto;

public class CategoryResponse {

    private Long categoryId;
    private String categoryName;
    private String description;
    private String iconUrl;
    private Boolean isActive;

    // Constructors
    public CategoryResponse() {
    }

    public CategoryResponse(Long categoryId, String categoryName, String description, String iconUrl,
            Boolean isActive) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.description = description;
        this.iconUrl = iconUrl;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
