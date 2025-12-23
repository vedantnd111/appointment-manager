package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findByVendor_VendorId(Long vendorId);
}
