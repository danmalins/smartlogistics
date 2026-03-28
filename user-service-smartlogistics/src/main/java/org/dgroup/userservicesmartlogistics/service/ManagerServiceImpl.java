package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.manager.CreateDriverRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverTruckInfoRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.DriverNotFoundException;
import org.dgroup.userservicesmartlogistics.model.*;
import org.dgroup.userservicesmartlogistics.repository.DriverProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.ManagerProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerProfileRepository managerProfileRepository;
    private final DriverProfileRepository driverProfileRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void createDriver(CreateDriverRequestDTO dto, Authentication authentication) {
        String managerEmail = authentication.getName();

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

        userRepository.save(user);

        DriverProfile driver = DriverProfile.builder()
                .user(user)
                .driverLicenseNumber(dto.getDriverLicenseNumber())
                .truckType(dto.getTruckType())
                .truckCapacityWeight(dto.getTruckCapacityWeight())
                .truckCapacityVolume(dto.getTruckCapacityVolume())
                .status(DriverStatus.OFFLINE)
                .rating(0.0)
                .completedDeliveries(0)
                .currentLatitude(0.0)
                .currentLongitude(0.0)
                .createdAt(LocalDateTime.now())
                .build();

        driverProfileRepository.save(driver);
    }

    @Override
    public List<DriverProfile> getAvailableDrivers() {
        return driverProfileRepository.findByStatus(DriverStatus.AVAILABLE);
    }

    @Override
    public DriverProfile getDriver(UUID driverId) {
        return driverProfileRepository.findById(driverId)
                .orElseThrow(() -> new DriverNotFoundException("Driver not found"));
    }

    @Override
    public List<DriverProfile> getDriversByStatus(DriverStatus status) {
        return driverProfileRepository.findByStatus(status);
    }

    @Override
    public DriverProfile updateTruckInfo(UUID id, UpdateDriverTruckInfoRequestDTO request,
                                         Authentication authentication) {

        if(!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can update driver truck info.");

        DriverProfile driver = driverProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setTruckType(request.getTruckType());
        driver.setTruckCapacityWeight(request.getTruckCapacityWeight());
        driver.setTruckCapacityVolume(request.getTruckCapacityVolume());

        return driverProfileRepository.save(driver);
    }

    @Override
    public DriverProfile updateDriverLicenseNumber(UUID id, UpdateDriverLicenseNumberRequestDTO request, Authentication authentication) {
        if(!isManager(authentication) && !isAdmin(authentication))
            throw new CustomAccessDeniedException("Only manager or admin can update driver license number.");

        DriverProfile driver = driverProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        driver.setDriverLicenseNumber(request.getDriverLicenseNumber());

        return driverProfileRepository.save(driver);
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
