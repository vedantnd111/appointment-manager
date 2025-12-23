package com.aspirants.appointment_manager.dto;

public class SendOtpResponse {

    private String message;
    private Integer expiresIn; // in seconds

    public SendOtpResponse() {
    }

    public SendOtpResponse(String message, Integer expiresIn) {
        this.message = message;
        this.expiresIn = expiresIn;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }
}
