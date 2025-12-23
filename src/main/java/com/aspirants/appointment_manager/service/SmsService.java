package com.aspirants.appointment_manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    /**
     * Mock SMS service - logs OTP to console instead of sending actual SMS
     * In production, integrate with Twilio, AWS SNS, MSG91, or other SMS provider
     */
    public void sendOtp(String phoneNo, String otp) {
        // Mock implementation - just log to console
        logger.info("==============================================");
        logger.info("SMS SENT TO: {}", phoneNo);
        logger.info("MESSAGE: Your OTP for Appointment Manager is: {}", otp);
        logger.info("This OTP is valid for 5 minutes.");
        logger.info("==============================================");

        // In production, replace with actual SMS API call:
        // Example with Twilio:
        // twilioClient.sendSms(phoneNo, "Your OTP is: " + otp + ". Valid for 5
        // minutes.");
    }
}
