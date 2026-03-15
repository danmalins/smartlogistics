package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;

import java.util.List;
import java.util.UUID;

public interface ManagerService {
    List<DriverProfile> getAvailableDrivers();

    DriverProfile getDriver(UUID driverId);

    List<DriverProfile> getDriversByStatus(DriverStatus status);
}
