package com.aspirants.appointment_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponse {

    private Long favoriteId;
    private Long userId;
    private Long vendorId;
    private String vendorName;
    private String vendorIconUrl;
    private LocalDateTime createdAt;

}
