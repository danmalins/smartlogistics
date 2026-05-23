package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;

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
}
