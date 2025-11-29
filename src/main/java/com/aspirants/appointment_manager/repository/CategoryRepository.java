package com.aspirants.appointment_manager.repository;

import com.aspirants.appointment_manager.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByIsActive(Boolean isActive);

    boolean existsByCategoryName(String categoryName);
}
