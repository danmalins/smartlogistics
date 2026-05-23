package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.driver.CreateDriverRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.driver.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;

public interface DriverService {

    DriverProfile getDriverProfile(Authentication authentication);

    DriverProfile updateDriverLocation(
            BigDecimal latitude,
            BigDecimal longitude,
            Authentication authentication
    );

    DriverProfile updateDriverStatus(
            DriverStatus status,
            Authentication authentication
    );

    DriverProfile createDriver(CreateDriverRequestDTO dto, Authentication authentication);

    List<DriverProfile> getAllDrivers(Authentication authentication);

    List<DriverProfile> getAvailableDrivers(Authentication authentication);

    DriverProfile getDriver(String email, Authentication authentication);

    List<DriverProfile> getDriversByStatus(DriverStatus status, Authentication authentication);

    DriverProfile updateDriverLicenseNumber(String email, UpdateDriverLicenseNumberRequestDTO request, Authentication authentication);
}
