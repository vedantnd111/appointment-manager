package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.CategoryResponse;
import com.aspirants.appointment_manager.dto.ServiceRequest;
import com.aspirants.appointment_manager.dto.ServiceResponse;
import com.aspirants.appointment_manager.entity.Category;
import com.aspirants.appointment_manager.entity.Service;
import com.aspirants.appointment_manager.entity.Store;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.CategoryRepository;
import com.aspirants.appointment_manager.repository.ServiceRepository;
import com.aspirants.appointment_manager.repository.StoreRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class ServiceService {

    private final ServiceRepository serviceRepository;
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    public ServiceService(ServiceRepository serviceRepository,
            CategoryRepository categoryRepository,
            StoreRepository storeRepository) {
        this.serviceRepository = serviceRepository;
        this.categoryRepository = categoryRepository;
        this.storeRepository = storeRepository;
    }

    public ServiceResponse createService(ServiceRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        Store store = storeRepository.findById(request.getStoreId()) // request.getVendorId() should be storeId logic
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", request.getStoreId()));

        Service service = new Service();
        service.setCategory(category);
        service.setStore(store);
        service.setServiceName(request.getServiceName());
        service.setDescription(request.getDescription());
        service.setDuration(request.getDuration());
        service.setPrice(request.getPrice());
        service.setIsActive(true);

        Service savedService = serviceRepository.save(service);
        return mapToResponse(savedService);
    }

    @Transactional(readOnly = true)
    public ServiceResponse getServiceById(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", id));
        return mapToResponse(service);
    }

    @Transactional(readOnly = true)
    public List<ServiceResponse> getAllServices() {
        return serviceRepository.findByIsActive(true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ServiceResponse> getServicesByStore(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeId));

        return serviceRepository.findByStoreAndIsActive(store, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ServiceResponse> getServicesByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        return serviceRepository.findByCategoryAndIsActive(category, true).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ServiceResponse updateService(Long id, ServiceRequest request) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", id));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.getCategoryId()));

        service.setCategory(category);
        service.setServiceName(request.getServiceName());
        service.setDescription(request.getDescription());
        service.setDuration(request.getDuration());
        service.setPrice(request.getPrice());

        Service updatedService = serviceRepository.save(service);
        return mapToResponse(updatedService);
    }

    public void deleteService(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service", "id", id));

        service.setIsActive(false);
        serviceRepository.save(service);
    }

    private ServiceResponse mapToResponse(Service service) {
        CategoryResponse categoryResponse = new CategoryResponse(
                service.getCategory().getCategoryId(),
                service.getCategory().getCategoryName(),
                service.getCategory().getDescription(),
                service.getCategory().getIconUrl(),
                service.getCategory().getIsActive());

        return new ServiceResponse(
                service.getServiceId(),
                categoryResponse,
                service.getStore().getStoreId(),
                service.getStore().getStoreName(),
                service.getServiceName(),
                service.getDescription(),
                service.getDuration(),
                service.getPrice(),
                service.getIsActive());
    }
}
