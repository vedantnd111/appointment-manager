package com.aspirants.appointment_manager.dto;

import com.aspirants.appointment_manager.enums.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long notificationId;
    private Long userId;
    private String title;
    private String message;
    private NotificationType notificationType;
    private Boolean isRead;
    private LocalDateTime createdAt;

}
