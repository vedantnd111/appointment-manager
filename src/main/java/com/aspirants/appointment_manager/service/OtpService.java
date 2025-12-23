package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.entity.Otp;
import com.aspirants.appointment_manager.exception.ExpiredOtpException;
import com.aspirants.appointment_manager.exception.InvalidOtpException;
import com.aspirants.appointment_manager.exception.OtpRateLimitException;
import com.aspirants.appointment_manager.repository.OtpRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OtpService {

    private final OtpRepository otpRepository;
    private final SmsService smsService;
    private final SecureRandom secureRandom;

    @Value("${otp.expiration}")
    private Integer otpExpiration; // in seconds

    @Value("${otp.length}")
    private Integer otpLength;

    @Value("${otp.max.attempts}")
    private Integer maxAttempts;

    @Value("${otp.rate.limit.minutes}")
    private Integer rateLimitMinutes;

    public OtpService(OtpRepository otpRepository, SmsService smsService) {
        this.otpRepository = otpRepository;
        this.smsService = smsService;
        this.secureRandom = new SecureRandom();
    }

    /**
     * Generate and send OTP to phone number
     */
    @Transactional
    public void generateAndSendOtp(String phoneNo) {
        // Check rate limiting
        checkRateLimit(phoneNo);

        // Generate OTP
        String otpCode = generateOtpCode();

        // Calculate expiration time
        LocalDateTime expiresAt = LocalDateTime.now().plusSeconds(otpExpiration);

        // Save OTP to database
        Otp otp = new Otp(phoneNo, otpCode, expiresAt);
        otpRepository.save(otp);

        // Send OTP via SMS
        smsService.sendOtp(phoneNo, otpCode);
    }

    /**
     * Verify OTP for phone number
     */
    @Transactional
    public boolean verifyOtp(String phoneNo, String otpCode) {
        // Find the latest valid OTP for this phone number
        Optional<Otp> otpOptional = otpRepository.findLatestValidOtp(phoneNo, LocalDateTime.now());

        if (otpOptional.isEmpty()) {
            throw new InvalidOtpException("Invalid or expired OTP");
        }

        Otp otp = otpOptional.get();

        // Check if OTP is expired
        if (otp.isExpired()) {
            throw new ExpiredOtpException("OTP has expired");
        }

        // Verify OTP code
        if (!otp.getOtpCode().equals(otpCode)) {
            throw new InvalidOtpException("Invalid OTP code");
        }

        // Mark OTP as verified
        otp.setIsVerified(true);
        otpRepository.save(otp);

        return true;
    }

    /**
     * Generate random OTP code
     */
    private String generateOtpCode() {
        int bound = (int) Math.pow(10, otpLength);
        int otp = secureRandom.nextInt(bound);
        return String.format("%0" + otpLength + "d", otp);
    }

    /**
     * Check if user has exceeded rate limit for OTP requests
     */
    private void checkRateLimit(String phoneNo) {
        LocalDateTime since = LocalDateTime.now().minusMinutes(rateLimitMinutes);
        List<Otp> recentOtps = otpRepository.findRecentOtpsByPhoneNo(phoneNo, since);

        if (recentOtps.size() >= maxAttempts) {
            throw new OtpRateLimitException(
                    "Too many OTP requests. Please try again after " + rateLimitMinutes + " minutes.");
        }
    }

    /**
     * Clean up expired OTPs (can be scheduled to run periodically)
     */
    @Transactional
    public void cleanupExpiredOtps() {
        otpRepository.deleteExpiredOtps(LocalDateTime.now());
    }
}
