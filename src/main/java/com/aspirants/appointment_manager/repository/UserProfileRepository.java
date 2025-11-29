package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByEmailId(String emailId);

    List<UserProfile> findByIsActive(Boolean isActive);

    boolean existsByEmailId(String emailId);
}
