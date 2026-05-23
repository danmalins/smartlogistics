package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.driver.CreateDriverRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.driver.UpdateDriverLicenseNumberRequestDTO;
import org.dgroup.userservicesmartlogistics.dto.request.driver.UpdateDriverTruckInfoRequestDTO;
import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ManagerService {

    DriverProfile createDriver(CreateDriverRequestDTO dto, Authentication authentication);

    ClientProfile getClient(String email, Authentication authentication);

    List<ClientProfile> getAllClients(Authentication authentication);

    List<DriverProfile> getAllDrivers(Authentication authentication);

    List<DriverProfile> getAvailableDrivers(Authentication authentication);

    DriverProfile getDriver(String email, Authentication authentication);

    List<DriverProfile> getDriversByStatus(DriverStatus status, Authentication authentication);

//    DriverProfile updateTruckInfo(String email, UpdateDriverTruckInfoRequestDTO request,
//            Authentication authentication
//    );

    DriverProfile updateDriverLicenseNumber(String email, UpdateDriverLicenseNumberRequestDTO request, Authentication authentication);
}
