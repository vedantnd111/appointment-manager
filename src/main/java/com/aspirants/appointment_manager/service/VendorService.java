package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.*;
import com.aspirants.appointment_manager.entity.Address;
import com.aspirants.appointment_manager.entity.Category;
import com.aspirants.appointment_manager.entity.VendorAvailability;
import com.aspirants.appointment_manager.entity.VendorProfile;
import com.aspirants.appointment_manager.exception.DuplicateResourceException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.AddressRepository;
import com.aspirants.appointment_manager.repository.CategoryRepository;
import com.aspirants.appointment_manager.repository.VendorAvailabilityRepository;
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
    private final AddressRepository addressRepository;
    private final VendorAvailabilityRepository availabilityRepository;

    public VendorService(VendorProfileRepository vendorRepository,
            CategoryRepository categoryRepository,
            AddressRepository addressRepository,
            VendorAvailabilityRepository availabilityRepository) {
        this.vendorRepository = vendorRepository;
        this.categoryRepository = categoryRepository;
        this.addressRepository = addressRepository;
        this.availabilityRepository = availabilityRepository;
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

        Address address = mapToAddressEntity(request.getAddress());
        address = addressRepository.save(address);

        VendorProfile vendor = new VendorProfile();
        vendor.setVendorName(request.getVendorName());
        vendor.setCategory(category);
        vendor.setEmailId(request.getEmailId());
        vendor.setPhoneNo(request.getPhoneNo());
        vendor.setDescription(request.getDescription());
        vendor.setAddress(address);
        vendor.setGstNumber(request.getGstNumber());
        vendor.setAverageRating(BigDecimal.ZERO);
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

        updateAddress(vendor.getAddress(), request.getAddress());

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

    // Availability Methods

    public VendorAvailabilityResponse addAvailability(Long vendorId, VendorAvailabilityRequest request) {
        VendorProfile vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", vendorId));

        // Check if availability already exists for this day
        availabilityRepository.findByVendorAndDayOfWeek(vendor, request.getDayOfWeek())
                .ifPresent(a -> {
                    throw new DuplicateResourceException("Availability", "dayOfWeek", request.getDayOfWeek());
                });

        VendorAvailability availability = new VendorAvailability();
        availability.setVendor(vendor);
        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setIsAvailable(request.getIsAvailable());

        VendorAvailability savedAvailability = availabilityRepository.save(availability);
        return mapToAvailabilityResponse(savedAvailability);
    }

    public List<VendorAvailabilityResponse> getVendorAvailability(Long vendorId) {
        VendorProfile vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", vendorId));

        return availabilityRepository.findByVendor(vendor).stream()
                .map(this::mapToAvailabilityResponse)
                .collect(Collectors.toList());
    }

    public VendorAvailabilityResponse updateAvailability(Long vendorId, Long availabilityId,
            VendorAvailabilityRequest request) {
        VendorAvailability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", availabilityId));

        if (!availability.getVendor().getVendorId().equals(vendorId)) {
            throw new ResourceNotFoundException("Availability", "vendorId", vendorId);
        }

        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setIsAvailable(request.getIsAvailable());

        VendorAvailability updatedAvailability = availabilityRepository.save(availability);
        return mapToAvailabilityResponse(updatedAvailability);
    }

    // Helpers

    private Address mapToAddressEntity(AddressDTO dto) {
        Address address = new Address();
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPincode(dto.getPincode());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());
        address.setType(dto.getType());
        return address;
    }

    private void updateAddress(Address address, AddressDTO dto) {
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setCountry(dto.getCountry());
        address.setPincode(dto.getPincode());
        address.setLatitude(dto.getLatitude());
        address.setLongitude(dto.getLongitude());
        address.setType(dto.getType());
    }

    private VendorProfileResponse mapToResponse(VendorProfile vendor) {
        AddressDTO addressDTO = new AddressDTO(
                vendor.getAddress().getAddressId(),
                vendor.getAddress().getAddressLine1(),
                vendor.getAddress().getAddressLine2(),
                vendor.getAddress().getCity(),
                vendor.getAddress().getState(),
                vendor.getAddress().getCountry(),
                vendor.getAddress().getPincode(),
                vendor.getAddress().getLatitude(),
                vendor.getAddress().getLongitude(),
                vendor.getAddress().getType());

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
                addressDTO,
                vendor.getGstNumber(),
                vendor.getAverageRating(),
                vendor.getIsActive());
    }

    private VendorAvailabilityResponse mapToAvailabilityResponse(VendorAvailability availability) {
        return new VendorAvailabilityResponse(
                availability.getAvailabilityId(),
                availability.getDayOfWeek(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getIsAvailable());
    }
}
