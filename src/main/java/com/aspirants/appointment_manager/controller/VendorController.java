package com.aspirants.appointment_manager.controller;

import com.aspirants.appointment_manager.dto.VendorAvailabilityRequest;
import com.aspirants.appointment_manager.dto.VendorAvailabilityResponse;
import com.aspirants.appointment_manager.dto.VendorProfileRequest;
import com.aspirants.appointment_manager.dto.VendorProfileResponse;
import com.aspirants.appointment_manager.service.VendorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @PostMapping
    public ResponseEntity<VendorProfileResponse> createVendor(@Valid @RequestBody VendorProfileRequest request) {
        VendorProfileResponse response = vendorService.createVendor(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendorProfileResponse> getVendorById(@PathVariable Long id) {
        VendorProfileResponse response = vendorService.getVendorById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<VendorProfileResponse>> getAllVendors(
            @RequestParam(required = false) Long categoryId) {
        List<VendorProfileResponse> responses;
        if (categoryId != null) {
            responses = vendorService.getVendorsByCategory(categoryId);
        } else {
            responses = vendorService.getAllVendors();
        }
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendorProfileResponse> updateVendor(
            @PathVariable Long id,
            @Valid @RequestBody VendorProfileRequest request) {
        VendorProfileResponse response = vendorService.updateVendor(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.noContent().build();
    }

    // Availability Endpoints

    @PostMapping("/{id}/availability")
    public ResponseEntity<VendorAvailabilityResponse> addAvailability(
            @PathVariable Long id,
            @Valid @RequestBody VendorAvailabilityRequest request) {
        VendorAvailabilityResponse response = vendorService.addAvailability(id, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/availability")
    public ResponseEntity<List<VendorAvailabilityResponse>> getVendorAvailability(@PathVariable Long id) {
        List<VendorAvailabilityResponse> responses = vendorService.getVendorAvailability(id);
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}/availability/{availabilityId}")
    public ResponseEntity<VendorAvailabilityResponse> updateAvailability(
            @PathVariable Long id,
            @PathVariable Long availabilityId,
            @Valid @RequestBody VendorAvailabilityRequest request) {
        VendorAvailabilityResponse response = vendorService.updateAvailability(id, availabilityId, request);
        return ResponseEntity.ok(response);
    }
}
