package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.VendorAvailability;
import com.aspirants.appointment_manager.entity.VendorProfile;
import com.aspirants.appointment_manager.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorAvailabilityRepository extends JpaRepository<VendorAvailability, Long> {

    List<VendorAvailability> findByVendor(VendorProfile vendor);

    Optional<VendorAvailability> findByVendorAndDayOfWeek(VendorProfile vendor, DayOfWeek dayOfWeek);

    List<VendorAvailability> findByVendorAndIsAvailable(VendorProfile vendor, Boolean isAvailable);
}
