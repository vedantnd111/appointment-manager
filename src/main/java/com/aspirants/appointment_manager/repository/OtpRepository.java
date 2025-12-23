package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

    /**
     * Find the latest valid (non-verified, non-expired) OTP for a phone number
     */
    @Query("SELECT o FROM Otp o WHERE o.phoneNo = :phoneNo " +
            "AND o.isVerified = false " +
            "AND o.expiresAt > :currentTime " +
            "ORDER BY o.createdAt DESC")
    Optional<Otp> findLatestValidOtp(@Param("phoneNo") String phoneNo,
            @Param("currentTime") LocalDateTime currentTime);

    /**
     * Find all OTPs created for a phone number within a time window
     */
    @Query("SELECT o FROM Otp o WHERE o.phoneNo = :phoneNo " +
            "AND o.createdAt > :since " +
            "ORDER BY o.createdAt DESC")
    List<Otp> findRecentOtpsByPhoneNo(@Param("phoneNo") String phoneNo,
            @Param("since") LocalDateTime since);

    /**
     * Delete all expired OTPs
     */
    @Query("DELETE FROM Otp o WHERE o.expiresAt < :currentTime")
    void deleteExpiredOtps(@Param("currentTime") LocalDateTime currentTime);

    /**
     * Find OTP by phone number and code
     */
    Optional<Otp> findByPhoneNoAndOtpCode(String phoneNo, String otpCode);
}
