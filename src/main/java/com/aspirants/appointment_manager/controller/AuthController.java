package com.aspirants.appointment_manager.controller;

import com.aspirants.appointment_manager.dto.SendOtpRequest;
import com.aspirants.appointment_manager.dto.SendOtpResponse;
import com.aspirants.appointment_manager.dto.VerifyOtpRequest;
import com.aspirants.appointment_manager.dto.VerifyOtpResponse;
import com.aspirants.appointment_manager.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Send OTP to phone number
     * POST /api/auth/send-otp
     */
    @PostMapping("/send-otp")
    public ResponseEntity<SendOtpResponse> sendOtp(@Valid @RequestBody SendOtpRequest request) {
        SendOtpResponse response = authService.sendOtp(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Resend OTP to phone number (same as send-otp)
     * POST /api/auth/resend-otp
     */
    @PostMapping("/resend-otp")
    public ResponseEntity<SendOtpResponse> resendOtp(@Valid @RequestBody SendOtpRequest request) {
        SendOtpResponse response = authService.sendOtp(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Verify OTP and authenticate user
     * POST /api/auth/verify-otp
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<VerifyOtpResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest request) {
        VerifyOtpResponse response = authService.verifyOtp(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
