package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.NotificationResponse;
import com.aspirants.appointment_manager.entity.Notification;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.enums.NotificationType;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.NotificationRepository;
import com.aspirants.appointment_manager.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserProfileRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserProfileRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public void createNotification(Long userId, String title, String message, NotificationType type) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setNotificationType(type);
        notification.setIsRead(false);

        notificationRepository.save(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUserNotifications(Long userId, Boolean unreadOnly) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        List<Notification> notifications;
        if (Boolean.TRUE.equals(unreadOnly)) {
            notifications = notificationRepository.findByUserAndIsRead(user, false);
        } else {
            notifications = notificationRepository.findByUserOrderByCreatedAtDesc(user);
        }

        return notifications.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResourceNotFoundException("Notification", "id", notificationId));

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    public void deleteNotification(Long notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new ResourceNotFoundException("Notification", "id", notificationId);
        }
        notificationRepository.deleteById(notificationId);
    }

    private NotificationResponse mapToResponse(Notification notification) {
        return new NotificationResponse(
                notification.getNotificationId(),
                notification.getUser().getUserId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getNotificationType(),
                notification.getIsRead(),
                notification.getCreatedAt());
    }
}
