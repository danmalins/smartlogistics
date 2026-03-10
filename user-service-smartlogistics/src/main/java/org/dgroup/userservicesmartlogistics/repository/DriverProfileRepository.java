package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.dgroup.userservicesmartlogistics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DriverProfileRepository  extends JpaRepository<DriverProfile,String> {

    Optional<DriverProfile> findById(String id);

    Optional<DriverProfile> findByUsername(String username);

    Optional<DriverProfile> findByEmail(String email);

    Optional<DriverProfile> findByTruckType(String truckType);

    Optional<DriverProfile> findByDriverStatus (String driverStatus);

    Optional<DriverProfile> findByLocation (String currentLattitude, String currentLongtitude);

    Optional<DriverProfile> findByRating (String rating);



}
