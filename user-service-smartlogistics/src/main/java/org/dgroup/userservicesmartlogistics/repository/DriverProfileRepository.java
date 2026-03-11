package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverProfileRepository  extends JpaRepository<DriverProfile,String> {

    Optional<DriverProfile> findByDriverLicenseNumber(String driverLicenseNumber);

    Optional<DriverProfile> findByTruckType(String truckType);

    Optional<DriverStatus> findByStatus (DriverStatus status);

    List<DriverProfile> findNearestDrivers(Double latitude, Double longitude);

    Optional<DriverProfile> findByRating (Double rating);

    boolean existsByDriverLicenseNumber(String driverLicenseNumber);
}
