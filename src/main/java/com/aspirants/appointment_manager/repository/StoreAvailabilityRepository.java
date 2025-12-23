package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Store;
import com.aspirants.appointment_manager.entity.StoreAvailability;
import com.aspirants.appointment_manager.enums.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreAvailabilityRepository extends JpaRepository<StoreAvailability, Long> {
    List<StoreAvailability> findByStore(Store store);

    Optional<StoreAvailability> findByStoreAndDayOfWeek(Store store, DayOfWeek dayOfWeek);
}
