package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverProfileRepository  extends JpaRepository<DriverProfile, UUID> {

    Optional<DriverProfile> findByDriverLicenseNumber(String driverLicenseNumber);

    Optional<DriverProfile> findByTruckType(String truckType);

    List<DriverStatus> findByStatus (DriverStatus status);

    // Need to be @Query + Haversine formula
    List<DriverProfile> findNearestDrivers(Double latitude, Double longitude);

    Optional<DriverProfile> findByRating (Double rating);

    boolean existsByDriverLicenseNumber(String driverLicenseNumber);
}
