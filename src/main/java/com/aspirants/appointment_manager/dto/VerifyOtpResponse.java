package com.aspirants.appointment_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerifyOtpResponse {

    private String token;
    private UserProfileResponse user;
    private Boolean isNewUser;

}
