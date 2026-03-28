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

    public void createDriver(CreateDriverRequestDTO dto, Authentication authentication);

    List<DriverProfile> getAvailableDrivers();

    DriverProfile getDriver(UUID driverId);

    List<DriverProfile> getDriversByStatus(DriverStatus status);

    DriverProfile updateTruckInfo(UUID id, UpdateDriverTruckInfoRequestDTO request,
            Authentication authentication
    );

    DriverProfile updateDriverLicenseNumber(UUID id, UpdateDriverLicenseNumberRequestDTO request, Authentication authentication);
}
