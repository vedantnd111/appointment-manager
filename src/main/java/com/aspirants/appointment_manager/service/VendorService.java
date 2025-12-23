package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.*;
import com.aspirants.appointment_manager.entity.Category;
import com.aspirants.appointment_manager.entity.VendorProfile;
import com.aspirants.appointment_manager.exception.DuplicateResourceException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.CategoryRepository;
import com.aspirants.appointment_manager.repository.VendorProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VendorService {

    private final VendorProfileRepository vendorRepository;
    private final CategoryRepository categoryRepository;

    public VendorService(VendorProfileRepository vendorRepository,
            CategoryRepository categoryRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
    }

    public VendorProfileResponse createVendor(VendorProfileRequest request) {
        if (vendorRepository.existsByEmailId(request.getEmailId())) {
            throw new DuplicateResourceException("Vendor", "emailId", request.getEmailId());
        }
        if (vendorRepository.existsByGstNumber(request.getGstNumber())) {
            throw new DuplicateResourceException("Vendor", "gstNumber", request.getGstNumber());
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        VendorProfile vendor = new VendorProfile();
        vendor.setVendorName(request.getVendorName());
        vendor.setCategory(category);
        vendor.setEmailId(request.getEmailId());
        vendor.setPhoneNo(request.getPhoneNo());
        vendor.setDescription(request.getDescription());
        vendor.setGstNumber(request.getGstNumber());
        vendor.setIsActive(true);

        VendorProfile savedVendor = vendorRepository.save(vendor);
        return mapToResponse(savedVendor);
    }

    @Transactional(readOnly = true)
    public VendorProfileResponse getVendorById(Long id) {
        VendorProfile vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", id));
        return mapToResponse(vendor);
    }

    @Transactional(readOnly = true)
    public List<VendorProfileResponse> getAllVendors() {
        return vendorRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VendorProfileResponse> getVendorsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return vendorRepository.findByCategoryAndIsActive(category, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public VendorProfileResponse updateVendor(Long id, VendorProfileRequest request) {
        VendorProfile vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", id));

        if (!vendor.getEmailId().equals(request.getEmailId()) &&
                vendorRepository.existsByEmailId(request.getEmailId())) {
            throw new DuplicateResourceException("Vendor", "emailId", request.getEmailId());
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        vendor.setVendorName(request.getVendorName());
        vendor.setCategory(category);
        vendor.setEmailId(request.getEmailId());
        vendor.setPhoneNo(request.getPhoneNo());
        vendor.setDescription(request.getDescription());
        vendor.setGstNumber(request.getGstNumber());

        VendorProfile updatedVendor = vendorRepository.save(vendor);
        return mapToResponse(updatedVendor);
    }

    public void deleteVendor(Long id) {
        VendorProfile vendor = vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", id));

        vendor.setIsActive(false);
        vendorRepository.save(vendor);
    }

    // Helpers

    private VendorProfileResponse mapToResponse(VendorProfile vendor) {
        CategoryResponse categoryResponse = new CategoryResponse(
                vendor.getCategory().getCategoryId(),
                vendor.getCategory().getCategoryName(),
                vendor.getCategory().getDescription(),
                vendor.getCategory().getIconUrl(),
                vendor.getCategory().getIsActive());

        return new VendorProfileResponse(
                vendor.getVendorId(),
                vendor.getVendorName(),
                categoryResponse,
                vendor.getEmailId(),
                vendor.getPhoneNo(),
                vendor.getDescription(),
                null, // Address is now in Store
                vendor.getGstNumber(),
                BigDecimal.ZERO, // Rating moved to Store
                vendor.getIsActive());
    }
}
