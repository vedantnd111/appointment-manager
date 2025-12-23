package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.*;
import com.aspirants.appointment_manager.entity.Address;
import com.aspirants.appointment_manager.entity.Store;
import com.aspirants.appointment_manager.entity.StoreAvailability;
import com.aspirants.appointment_manager.entity.VendorProfile;
import com.aspirants.appointment_manager.exception.DuplicateResourceException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.AddressRepository;
import com.aspirants.appointment_manager.repository.StoreAvailabilityRepository;
import com.aspirants.appointment_manager.repository.StoreRepository;
import com.aspirants.appointment_manager.repository.VendorProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StoreService {

    private final StoreRepository storeRepository;
    private final VendorProfileRepository vendorRepository;
    private final AddressRepository addressRepository;
    private final StoreAvailabilityRepository availabilityRepository;

    public StoreService(StoreRepository storeRepository,
            VendorProfileRepository vendorRepository,
            AddressRepository addressRepository,
            StoreAvailabilityRepository availabilityRepository) {
        this.storeRepository = storeRepository;
        this.vendorRepository = vendorRepository;
        this.addressRepository = addressRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public StoreResponse createStore(StoreRequest request) {
        VendorProfile vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", request.getVendorId()));

        Address address = mapToAddressEntity(request.getAddress());
        address = addressRepository.save(address);

        Store store = new Store();
        store.setVendor(vendor);
        store.setStoreName(request.getStoreName());
        store.setEmailId(request.getEmailId());
        store.setPhoneNo(request.getPhoneNo());
        store.setDescription(request.getDescription());
        store.setAddress(address);
        store.setAverageRating(BigDecimal.ZERO);
        store.setIsActive(true);

        Store savedStore = storeRepository.save(store);
        return mapToResponse(savedStore);
    }

    @Transactional(readOnly = true)
    public StoreResponse getStoreById(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", id));
        return mapToResponse(store);
    }

    @Transactional(readOnly = true)
    public List<StoreResponse> getStoresByVendor(Long vendorId) {
        return storeRepository.findByVendor_VendorId(vendorId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public StoreResponse updateStore(Long id, StoreRequest request) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", id));

        updateAddress(store.getAddress(), request.getAddress());

        store.setStoreName(request.getStoreName());
        store.setEmailId(request.getEmailId());
        store.setPhoneNo(request.getPhoneNo());
        store.setDescription(request.getDescription());

        Store updatedStore = storeRepository.save(store);
        return mapToResponse(updatedStore);
    }

    public void deleteStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", id));

        store.setIsActive(false);
        storeRepository.save(store);
    }

    // Availability Methods

    public StoreAvailabilityResponse addAvailability(Long storeId, StoreAvailabilityRequest request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeId));

        // Check if availability already exists for this day
        availabilityRepository.findByStoreAndDayOfWeek(store, request.getDayOfWeek())
                .ifPresent(a -> {
                    throw new DuplicateResourceException("Availability", "dayOfWeek", request.getDayOfWeek());
                });

        StoreAvailability availability = new StoreAvailability();
        availability.setStore(store);
        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setIsAvailable(request.getIsAvailable());

        StoreAvailability savedAvailability = availabilityRepository.save(availability);
        return mapToAvailabilityResponse(savedAvailability);
    }

    public List<StoreAvailabilityResponse> getStoreAvailability(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeId));

        return availabilityRepository.findByStore(store).stream()
                .map(this::mapToAvailabilityResponse)
                .collect(Collectors.toList());
    }

    public StoreAvailabilityResponse updateAvailability(Long storeId, Long availabilityId,
            StoreAvailabilityRequest request) {
        StoreAvailability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", availabilityId));

        if (!availability.getStore().getStoreId().equals(storeId)) {
            throw new ResourceNotFoundException("Availability", "storeId", storeId);
        }

        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());
        availability.setIsAvailable(request.getIsAvailable());

        StoreAvailability updatedAvailability = availabilityRepository.save(availability);
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

    private StoreResponse mapToResponse(Store store) {
        AddressDTO addressDTO = new AddressDTO(
                store.getAddress().getAddressId(),
                store.getAddress().getAddressLine1(),
                store.getAddress().getAddressLine2(),
                store.getAddress().getCity(),
                store.getAddress().getState(),
                store.getAddress().getCountry(),
                store.getAddress().getPincode(),
                store.getAddress().getLatitude(),
                store.getAddress().getLongitude(),
                store.getAddress().getType());

        return new StoreResponse(
                store.getStoreId(),
                store.getVendor().getVendorId(),
                store.getVendor().getVendorName(),
                store.getStoreName(),
                store.getEmailId(),
                store.getPhoneNo(),
                store.getDescription(),
                addressDTO,
                store.getAverageRating(),
                store.getIsActive());
    }

    private StoreAvailabilityResponse mapToAvailabilityResponse(StoreAvailability availability) {
        return new StoreAvailabilityResponse(
                availability.getAvailabilityId(),
                availability.getDayOfWeek(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getIsAvailable());
    }
}
