package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.driver.CreateDriverRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.driver.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.DriverNotFoundException;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.dgroup.userservicesmartlogistics.model.User;
import org.dgroup.userservicesmartlogistics.model.UserRole;
import org.dgroup.userservicesmartlogistics.repository.DriverProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverServiceImpl implements DriverService {
    private final DriverProfileRepository driverProfileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DriverProfile createDriver(CreateDriverRequestDTO dto, Authentication authentication) {

        // проверка что это менеджер
        if (!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can create driver");

        if (userRepository.existsByEmail(dto.getEmail()))
            throw new RuntimeException("Email already exists");

        if (driverProfileRepository.existsByDriverLicenseNumber(dto.getDriverLicenseNumber()))
            throw new RuntimeException("Driver license already exists");

        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(UserRole.ROLE_DRIVER)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phone(dto.getPhone())
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.saveAndFlush(user);

        DriverProfile driver = DriverProfile.builder()
                .user(savedUser)
                .driverLicenseNumber(dto.getDriverLicenseNumber())
                .status(DriverStatus.OFFLINE)
                .rating(0.0)
                .completedDeliveries(0)
                .currentLatitude(BigDecimal.valueOf(0.0))
                .currentLongitude(BigDecimal.valueOf(0.0))
                .createdAt(LocalDateTime.now())
                .build();

        return driverProfileRepository.save(driver);
    }

    @Override
    public List<DriverProfile> getAllDrivers(Authentication authentication) {
        if (!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can get driver profiles");
        return driverProfileRepository.findAll();
    }

    @Override
    public List<DriverProfile> getAvailableDrivers(Authentication authentication) {
        if (!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can get available drivers");
        return driverProfileRepository.findByStatus(DriverStatus.AVAILABLE);
    }

    @Override
    public DriverProfile getDriver(String email, Authentication authentication) {
        if (!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can get driver profile");
        return driverProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));
    }

    @Override
    public List<DriverProfile> getDriversByStatus(DriverStatus status, Authentication authentication) {
        if (!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can get driver by status");
        return driverProfileRepository.findByStatus(status);
    }

    @Override
    public DriverProfile getDriverProfile(Authentication authentication) {
        String email = authentication.getName();

        return driverProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new DriverNotFoundException("Driver with email: '" + email + "' not found"));
    }

    @Override
    public DriverProfile updateDriverLocation(BigDecimal latitude, BigDecimal longitude, Authentication authentication) {
        String email = authentication.getName();

        DriverProfile driver = driverProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new DriverNotFoundException("Driver with email: '" + email + "' not found"));

        validateCoordinates(latitude, longitude);
        driver.setCurrentLatitude(latitude);
        driver.setCurrentLongitude(longitude);

        return driverProfileRepository.save(driver);
    }

    @Override
    public DriverProfile updateDriverLicenseNumber(String email, UpdateDriverLicenseNumberRequestDTO request, Authentication authentication) {
        if(!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can update driver license number.");

        DriverProfile driver = driverProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setDriverLicenseNumber(request.getDriverLicenseNumber());

        return driverProfileRepository.save(driver);
    }

    @Override
    public DriverProfile updateDriverStatus(DriverStatus status, Authentication authentication) {
        String email = authentication.getName();

        DriverProfile driver = driverProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new DriverNotFoundException("Driver with email: '" + email + "' not found"));
        driver.setStatus(status);

        return driverProfileRepository.save(driver);
    }


    private void validateCoordinates(BigDecimal latitude, BigDecimal longitude) {

        if (latitude == null || longitude == null)
            throw new IllegalArgumentException("Coordinates cannot be null");

        if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0
                || latitude.compareTo(BigDecimal.valueOf(90)) > 0)
            throw new IllegalArgumentException("Invalid latitude");

        if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0
                || longitude.compareTo(BigDecimal.valueOf(180)) > 0)
            throw new IllegalArgumentException("Invalid longitude");
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));
    }

    private boolean isManager(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_MANAGER"));
    }
}
