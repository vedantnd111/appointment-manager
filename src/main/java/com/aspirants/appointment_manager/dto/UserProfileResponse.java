package com.aspirants.appointment_manager.dto;

import com.aspirants.appointment_manager.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
