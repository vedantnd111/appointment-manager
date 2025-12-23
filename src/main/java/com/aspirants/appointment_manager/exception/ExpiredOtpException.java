package com.aspirants.appointment_manager.exception;

public class ExpiredOtpException extends RuntimeException {
    public ExpiredOtpException(String message) {
        super(message);
    }
}
