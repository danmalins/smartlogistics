package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.UpdateDriverTruckInfoRequestDTO;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface DriverService {

    DriverProfile getDriverProfile(Authentication authentication);

    DriverProfile updateTruckInfo(
            UpdateDriverTruckInfoRequestDTO request,
            Authentication authentication
    );

    DriverProfile updateDriverLocation(
            Double latitude,
            Double longitude,
            Authentication authentication
    );

    DriverProfile updateDriverStatus(
            DriverStatus status,
            Authentication authentication
    );
}
