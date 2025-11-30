package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Category;
import com.aspirants.appointment_manager.entity.Service;
import com.aspirants.appointment_manager.entity.VendorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByVendor(VendorProfile vendor);

    List<Service> findByCategory(Category category);

    List<Service> findByVendorAndIsActive(VendorProfile vendor, Boolean isActive);

    List<Service> findByCategoryAndIsActive(Category category, Boolean isActive);

    List<Service> findByIsActive(Boolean isActive);
}
