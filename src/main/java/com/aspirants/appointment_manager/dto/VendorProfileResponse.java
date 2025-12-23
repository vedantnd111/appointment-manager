package com.aspirants.appointment_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VendorProfileResponse {

    private Long vendorId;
    private String vendorName;
    private CategoryResponse category;
    private String emailId;
    private String phoneNo;
    private String description;
    private AddressDTO address;
    private String gstNumber;
    private BigDecimal averageRating;
    private Boolean isActive;

}
