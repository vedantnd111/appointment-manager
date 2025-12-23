package com.aspirants.appointment_manager.dto;

public class VerifyOtpResponse {

    private String token;
    private UserProfileResponse user;
    private Boolean isNewUser;

    public VerifyOtpResponse() {
    }

    public VerifyOtpResponse(String token, UserProfileResponse user, Boolean isNewUser) {
        this.token = token;
        this.user = user;
        this.isNewUser = isNewUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserProfileResponse getUser() {
        return user;
    }

    public void setUser(UserProfileResponse user) {
        this.user = user;
    }

    public Boolean getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(Boolean isNewUser) {
        this.isNewUser = isNewUser;
    }
}
