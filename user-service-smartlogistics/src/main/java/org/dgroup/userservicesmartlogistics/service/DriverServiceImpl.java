package org.dgroup.userservicesmartlogistics.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.dgroup.userservicesmartlogistics.exception.DriverNotFoundException;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.dgroup.userservicesmartlogistics.repository.DriverProfileRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
    public DriverProfile updateDriverLocation(BigDecimal latitude, BigDecimal longitude, Authentication authentication) {
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


    private void validateCoordinates(BigDecimal latitude, BigDecimal longitude) {

        if (latitude == null || longitude == null)
            throw new IllegalArgumentException("Coordinates cannot be null");

        if (latitude.compareTo(BigDecimal.valueOf(-90)) < 0
                || latitude.compareTo(BigDecimal.valueOf(90)) > 0)
            throw new IllegalArgumentException("Invalid latitude");

        if (longitude.compareTo(BigDecimal.valueOf(-180)) < 0
                || longitude.compareTo(BigDecimal.valueOf(180)) > 0)
            throw new IllegalArgumentException("Invalid longitude");
    }
}
