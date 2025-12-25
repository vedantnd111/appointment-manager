package com.aspirants.appointment_manager.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponse {

    private Long storeId;
    private Long vendorId;
    private String vendorName;
    private String storeName;
    private String emailId;
    private String phoneNo;
    private String description;
    private AddressDTO address;
    private BigDecimal averageRating;
    private Boolean isActive;

}
