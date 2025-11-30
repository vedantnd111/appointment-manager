package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Review;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.entity.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByVendor(VendorProfile vendor);

    List<Review> findByUser(UserProfile user);

    List<Review> findByVendorOrderByCreatedAtDesc(VendorProfile vendor);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.vendor = :vendor")
    BigDecimal calculateAverageRating(@Param("vendor") VendorProfile vendor);
}
