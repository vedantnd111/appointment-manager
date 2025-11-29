package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.FavoriteRequest;
import com.aspirants.appointment_manager.dto.FavoriteResponse;
import com.aspirants.appointment_manager.entity.Favorite;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.entity.VendorProfile;
import com.aspirants.appointment_manager.exception.DuplicateResourceException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.FavoriteRepository;
import com.aspirants.appointment_manager.repository.UserProfileRepository;
import com.aspirants.appointment_manager.repository.VendorProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserProfileRepository userRepository;
    private final VendorProfileRepository vendorRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
            UserProfileRepository userRepository,
            VendorProfileRepository vendorRepository) {
        this.favoriteRepository = favoriteRepository;
        this.userRepository = userRepository;
        this.vendorRepository = vendorRepository;
    }

    public FavoriteResponse addFavorite(FavoriteRequest request) {
        UserProfile user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getUserId()));

        VendorProfile vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", request.getVendorId()));

        if (favoriteRepository.existsByUserAndVendor(user, vendor)) {
            throw new DuplicateResourceException("Favorite", "vendorId", request.getVendorId());
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setVendor(vendor);

        Favorite savedFavorite = favoriteRepository.save(favorite);
        return mapToResponse(savedFavorite);
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getUserFavorites(Long userId) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return favoriteRepository.findByUser(user).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void removeFavorite(Long id) {
        if (!favoriteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Favorite", "id", id);
        }
        favoriteRepository.deleteById(id);
    }

    public void removeFavoriteByUserAndVendor(Long userId, Long vendorId) {
        UserProfile user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        VendorProfile vendor = vendorRepository.findById(vendorId)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", vendorId));

        favoriteRepository.deleteByUserAndVendor(user, vendor);
    }

    private FavoriteResponse mapToResponse(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getFavoriteId(),
                favorite.getUser().getUserId(),
                favorite.getVendor().getVendorId(),
                favorite.getVendor().getVendorName(),
                favorite.getVendor().getCategory().getIconUrl(), // Using category icon as vendor icon for now
                favorite.getCreatedAt());
    }
}
