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
public class ServiceResponse {

    private Long serviceId;
    private CategoryResponse category;
    private Long storeId;
    private String storeName;
    private String serviceName;
    private String description;
    private Integer duration;
    private BigDecimal price;
    private Boolean isActive;

}
