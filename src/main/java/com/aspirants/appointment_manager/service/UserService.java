package com.aspirants.appointment_manager.service;

import com.aspirants.appointment_manager.dto.AddressDTO;
import com.aspirants.appointment_manager.dto.UserProfileRequest;
import com.aspirants.appointment_manager.dto.UserProfileResponse;
import com.aspirants.appointment_manager.entity.Address;
import com.aspirants.appointment_manager.entity.UserProfile;
import com.aspirants.appointment_manager.exception.DuplicateResourceException;
import com.aspirants.appointment_manager.exception.ResourceNotFoundException;
import com.aspirants.appointment_manager.repository.AddressRepository;
import com.aspirants.appointment_manager.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserProfileRepository userRepository;
    private final AddressRepository addressRepository;

    public UserService(UserProfileRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public UserProfileResponse createUser(UserProfileRequest request) {
        if (userRepository.existsByEmailId(request.getEmailId())) {
            throw new DuplicateResourceException("User", "emailId", request.getEmailId());
        }

        Address address = mapToAddressEntity(request.getAddress());
        address = addressRepository.save(address);

        UserProfile user = new UserProfile();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmailId(request.getEmailId());
        user.setPhoneNo(request.getPhoneNo());
        user.setBirthDate(request.getBirthDate());
        user.setGender(request.getGender());
        user.setAddress(address);
        user.setIsActive(true);

        UserProfile savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserById(Long id) {
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserProfileResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public UserProfileResponse updateUser(Long id, UserProfileRequest request) {
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (!user.getEmailId().equals(request.getEmailId()) &&
                userRepository.existsByEmailId(request.getEmailId())) {
            throw new DuplicateResourceException("User", "emailId", request.getEmailId());
        }

        // Update address
        updateAddress(user.getAddress(), request.getAddress());

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmailId(request.getEmailId());
        user.setPhoneNo(request.getPhoneNo());
        user.setBirthDate(request.getBirthDate());
        user.setGender(request.getGender());

        UserProfile updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    public void deleteUser(Long id) {
        UserProfile user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        user.setIsActive(false);
        userRepository.save(user);
    }

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

    private UserProfileResponse mapToResponse(UserProfile user) {
        AddressDTO addressDTO = new AddressDTO(
                user.getAddress().getAddressId(),
                user.getAddress().getAddressLine1(),
                user.getAddress().getAddressLine2(),
                user.getAddress().getCity(),
                user.getAddress().getState(),
                user.getAddress().getCountry(),
                user.getAddress().getPincode(),
                user.getAddress().getLatitude(),
                user.getAddress().getLongitude(),
                user.getAddress().getType());

        return new UserProfileResponse(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmailId(),
                user.getPhoneNo(),
                user.getBirthDate(),
                user.getGender(),
                addressDTO,
                user.getCreatedAt(),
                user.getIsActive());
    }
}
