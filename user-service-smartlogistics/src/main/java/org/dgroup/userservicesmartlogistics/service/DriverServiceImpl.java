package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.exception.DriverNotFoundException;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.dgroup.userservicesmartlogistics.repository.DriverProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DriverServiceImpl implements DriverService {
    private final DriverProfileRepository driverProfileRepository;

    @Override
    public DriverProfile getDriverProfile(Authentication authentication) {
        String email = authentication.getName();

        return driverProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new DriverNotFoundException("Driver with email: '" + email + "' not found"));
    }

    @Override
    public DriverProfile updateDriverLocation(Double latitude, Double longitude, Authentication authentication) {
        String email = authentication.getName();

        DriverProfile driver = driverProfileRepository.findByUserEmail(email)
                .orElseThrow(() -> new DriverNotFoundException("Driver with email: '" + email + "' not found"));

        validateCoordinates(latitude, longitude);
        driver.setCurrentLatitude(latitude);
        driver.setCurrentLongitude(longitude);

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


    private void validateCoordinates(Double latitude, Double longitude) {
        if (latitude < -90 || latitude > 90)
            throw new RuntimeException("Invalid latitude");
        if (longitude < -180 || longitude > 180)
            throw new RuntimeException("Invalid longitude");
    }
}
