package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.ClientProfile;
import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DriverProfileRepository extends JpaRepository<DriverProfile, UUID> {

    Optional<DriverProfile> findByUserEmail(String email);

    Optional<DriverProfile> findByDriverLicenseNumber(String driverLicenseNumber);

    List<DriverProfile> findByTruckType(String truckType);

    List<DriverProfile> findByStatus (DriverStatus status);

    // Need to be @Query + Haversine formula
    List<DriverProfile> findNearestDrivers(Double latitude, Double longitude);

    List<DriverProfile> findByRating (Double rating);

    boolean existsByDriverLicenseNumber(String driverLicenseNumber);
}
