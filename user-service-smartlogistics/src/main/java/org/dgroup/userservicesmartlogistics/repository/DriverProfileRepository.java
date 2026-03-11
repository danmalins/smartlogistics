package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverProfileRepository  extends JpaRepository<DriverProfile,String> {

    Optional<DriverProfile> findById(String id);

    Optional<DriverProfile> findByUsername(String username);

    Optional<DriverProfile> findByEmail(String email);

    Optional<DriverProfile> findByTruckType(String truckType);

    Optional<DriverProfile> findByDriverStatus (String driverStatus);

    Optional<DriverProfile> findByLocation (String currentLattitude, String currentLongtitude);

    Optional<DriverProfile> findByRating (String rating);



}
