package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Notification;
import com.aspirants.appointment_manager.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUser(UserProfile user);

    List<Notification> findByUserAndIsRead(UserProfile user, Boolean isRead);

    List<Notification> findByUserOrderByCreatedAtDesc(UserProfile user);
}
