package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.*;
import com.aspirants.appointment_manager.entity.UserProfile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final OtpService otpService;
    private final UserService userService;
    private final JwtService jwtService;

    public AuthService(OtpService otpService, UserService userService, JwtService jwtService) {
        this.otpService = otpService;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * Send OTP to phone number
     */
    public SendOtpResponse sendOtp(SendOtpRequest request) {
        otpService.generateAndSendOtp(request.getPhoneNo());
        return new SendOtpResponse("OTP sent successfully", 300); // 5 minutes = 300 seconds
    }

    /**
     * Verify OTP and authenticate user
     */
    @Transactional
    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        // Verify OTP
        otpService.verifyOtp(request.getPhoneNo(), request.getOtp());

        // Check if user exists
        UserProfile user = userService.findByPhoneNumber(request.getPhoneNo());
        boolean isNewUser = false;

        if (user == null) {
            // Create new user
            user = userService.createUserFromOtp(
                    request.getPhoneNo(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmailId());
            isNewUser = true;
        } else {
            // Update existing user's phone verification status if not already verified
            if (!user.getIsPhoneVerified()) {
                user.setIsPhoneVerified(true);
            }
        }

        // Update last login
        userService.updateLastLogin(user.getUserId());

        // Generate JWT token
        String token = jwtService.generateToken(user.getUserId(), user.getPhoneNo());

        // Create response
        UserProfileResponse userResponse = mapToUserResponse(user);
        return new VerifyOtpResponse(token, userResponse, isNewUser);
    }

    /**
     * Map UserProfile to UserProfileResponse
     */
    private UserProfileResponse mapToUserResponse(UserProfile user) {
        AddressDTO addressDTO = null;
        if (user.getAddress() != null) {
            addressDTO = new AddressDTO(
                    user.getAddress().getAddressId(),
                    user.getAddress().getAddressLine1(),
                    user.getAddress().getAddressLine2(),
                    user.getAddress().getCity(),
                    user.getAddress().getState(),
                    user.getAddress().getCountry(),
                    user.getAddress().getPincode(),
                    user.getAddress().getLatitude(),
                    user.getAddress().getLongitude(),
                    user.getAddress().getType());
        }

        return new UserProfileResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailId(),
                user.getPhoneNo(),
                user.getBirthDate(),
                user.getGender(),
                addressDTO,
                user.getCreatedAt(),
                user.getIsActive());
    }
}
