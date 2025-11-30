package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Favorite;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.entity.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    List<Favorite> findByUser(UserProfile user);

    List<Favorite> findByVendor(VendorProfile vendor);

    boolean existsByUserAndVendor(UserProfile user, VendorProfile vendor);

    void deleteByUserAndVendor(UserProfile user, VendorProfile vendor);
}
