package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.ServiceRequest;
import com.aspirants.appointment_manager.dto.ServiceResponse;
import com.aspirants.appointment_manager.entity.Category;
import com.aspirants.appointment_manager.entity.Service;
import com.aspirants.appointment_manager.entity.Store;
import com.aspirants.appointment_manager.repository.CategoryRepository;
import com.aspirants.appointment_manager.repository.ServiceRepository;
import com.aspirants.appointment_manager.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceServiceTest {

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private ServiceService serviceService;

    private Store store;
    private Category category;
    private Service service;

    @BeforeEach
    void setUp() {
        store = new Store();
        store.setStoreId(1L);
        store.setStoreName("Test Store");

        category = new Category();
        category.setCategoryId(1L);
        category.setCategoryName("Test Category");

        service = new Service();
        service.setServiceId(1L);
        service.setServiceName("Test Service");
        service.setDescription("Description");
        service.setDuration(60);
        service.setPrice(BigDecimal.valueOf(100));
        service.setStore(store);
        service.setCategory(category);
        service.setIsActive(true);
    }

    @Test
    void createService_Success() {
        ServiceRequest request = new ServiceRequest();
        request.setStoreId(1L);
        request.setCategoryId(1L);
        request.setServiceName("Test Service");
        request.setDescription("Description");
        request.setDuration(60);
        request.setPrice(BigDecimal.valueOf(100));

        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(serviceRepository.save(any(Service.class))).thenReturn(service);

        ServiceResponse response = serviceService.createService(request);

        assertNotNull(response);
        assertEquals("Test Service", response.getServiceName());
        verify(serviceRepository).save(any(Service.class));
    }

    @Test
    void getServicesByStore_Success() {
        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(serviceRepository.findByStoreAndIsActive(store, true)).thenReturn(Collections.singletonList(service));

        List<ServiceResponse> responses = serviceService.getServicesByStore(1L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("Test Service", responses.get(0).getServiceName());
    }
}
