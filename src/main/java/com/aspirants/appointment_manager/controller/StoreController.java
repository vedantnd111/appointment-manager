package com.aspirants.appointment_manager.controller;

import com.aspirants.appointment_manager.dto.StoreRequest;
import com.aspirants.appointment_manager.dto.StoreResponse;
import com.aspirants.appointment_manager.dto.StoreAvailabilityRequest;
import com.aspirants.appointment_manager.dto.StoreAvailabilityResponse;
import com.aspirants.appointment_manager.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreResponse> createStore(@Valid @RequestBody StoreRequest request) {
        StoreResponse response = storeService.createStore(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> getStoreById(@PathVariable Long id) {
        StoreResponse response = storeService.getStoreById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<StoreResponse>> getStoresByVendor(@PathVariable Long vendorId) {
        List<StoreResponse> responses = storeService.getStoresByVendor(vendorId);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StoreResponse> updateStore(
            @PathVariable Long id,
            @Valid @RequestBody StoreRequest request) {
        StoreResponse response = storeService.updateStore(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }

    // Availability Endpoints

    @PostMapping("/{id}/availability")
    public ResponseEntity<StoreAvailabilityResponse> addAvailability(
            @PathVariable Long id,
            @Valid @RequestBody StoreAvailabilityRequest request) {
        StoreAvailabilityResponse response = storeService.addAvailability(id, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<List<StoreAvailabilityResponse>> getStoreAvailability(@PathVariable Long id) {
        List<StoreAvailabilityResponse> responses = storeService.getStoreAvailability(id);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/availability/{availabilityId}")
    public ResponseEntity<StoreAvailabilityResponse> updateAvailability(
            @PathVariable Long id,
            @PathVariable Long availabilityId,
            @Valid @RequestBody StoreAvailabilityRequest request) {
        StoreAvailabilityResponse response = storeService.updateAvailability(id, availabilityId, request);
        return ResponseEntity.ok(response);
    }
}
