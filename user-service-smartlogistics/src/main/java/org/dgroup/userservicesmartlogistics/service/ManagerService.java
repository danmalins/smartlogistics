package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.manager.CreateDriverRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.UpdateDriverTruckInfoRequestDTO;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface ManagerService {

    DriverProfile createDriver(CreateDriverRequestDTO dto, Authentication authentication);

    List<DriverProfile> getAvailableDrivers(Authentication authentication);

    DriverProfile getDriver(UUID driverId, Authentication authentication);

    List<DriverProfile> getDriversByStatus(DriverStatus status, Authentication authentication);

    DriverProfile updateTruckInfo(UUID id, UpdateDriverTruckInfoRequestDTO request,
            Authentication authentication
    );

    DriverProfile updateDriverLicenseNumber(UUID id, UpdateDriverLicenseNumberRequestDTO request, Authentication authentication);
}
