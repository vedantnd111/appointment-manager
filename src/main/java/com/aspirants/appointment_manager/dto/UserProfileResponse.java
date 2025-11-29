package com.aspirants.appointment_manager.dto;

import com.aspirants.appointment_manager.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserProfileResponse {

    private Long userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String phoneNo;
    private LocalDate birthDate;
    private Gender gender;
    private AddressDTO address;
    private LocalDateTime createdAt;
    private Boolean isActive;

    // Constructors
    public UserProfileResponse() {
    }

    public UserProfileResponse(Long userId, String firstName, String lastName, String emailId, String phoneNo,
            LocalDate birthDate, Gender gender, AddressDTO address, LocalDateTime createdAt, Boolean isActive) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
