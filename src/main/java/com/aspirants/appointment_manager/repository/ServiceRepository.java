package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Category;
import com.aspirants.appointment_manager.entity.Service;
import com.aspirants.appointment_manager.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    List<Service> findByStore(Store store);

    List<Service> findByCategory(Category category);

    List<Service> findByStoreAndIsActive(Store store, Boolean isActive);

    List<Service> findByCategoryAndIsActive(Category category, Boolean isActive);

    List<Service> findByIsActive(Boolean isActive);
}
