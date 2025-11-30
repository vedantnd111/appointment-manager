package com.aspirants.appointment_manager.controller;

import com.aspirants.appointment_manager.dto.FavoriteRequest;
import com.aspirants.appointment_manager.dto.FavoriteResponse;
import com.aspirants.appointment_manager.service.FavoriteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<FavoriteResponse> addFavorite(@Valid @RequestBody FavoriteRequest request) {
        FavoriteResponse response = favoriteService.addFavorite(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteResponse>> getUserFavorites(@PathVariable Long userId) {
        List<FavoriteResponse> responses = favoriteService.getUserFavorites(userId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long id) {
        favoriteService.removeFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
