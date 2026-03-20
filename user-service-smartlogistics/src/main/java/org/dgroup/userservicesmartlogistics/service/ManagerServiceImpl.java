package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverTruckInfoRequestDTO;
import org.dgroup.userservicesmartlogistics.exception.CustomAccessDeniedException;
import org.dgroup.userservicesmartlogistics.exception.DriverNotFoundException;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.dgroup.userservicesmartlogistics.repository.DriverProfileRepository;
import org.dgroup.userservicesmartlogistics.repository.ManagerProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final ManagerProfileRepository managerProfileRepository;
    private final DriverProfileRepository driverProfileRepository;


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

        if(!isManager(authentication) || !isAdmin(authentication))
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
        if(!isManager(authentication) || !isAdmin(authentication))
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
