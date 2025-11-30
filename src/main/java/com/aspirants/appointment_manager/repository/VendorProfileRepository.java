package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Category;
import com.aspirants.appointment_manager.entity.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendorProfileRepository extends JpaRepository<VendorProfile, Long> {

    Optional<VendorProfile> findByEmailId(String emailId);

    List<VendorProfile> findByCategory(Category category);

    List<VendorProfile> findByIsActive(Boolean isActive);

    List<VendorProfile> findByCategoryAndIsActive(Category category, Boolean isActive);

    @Query("SELECT v FROM VendorProfile v WHERE v.averageRating >= :minRating AND v.isActive = true")
    List<VendorProfile> findByMinimumRating(@Param("minRating") BigDecimal minRating);

    boolean existsByEmailId(String emailId);

    boolean existsByGstNumber(String gstNumber);
}
