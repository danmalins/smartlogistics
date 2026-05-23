package org.dgroup.userservicesmartlogistics.repository;

import org.dgroup.userservicesmartlogistics.model.ManagerProfile;
import org.dgroup.userservicesmartlogistics.model.Truck;
import org.dgroup.userservicesmartlogistics.model.TruckStatus;
import org.dgroup.userservicesmartlogistics.model.TruckType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TruckRepository extends JpaRepository<Truck, UUID> {

    List<Truck> findByStatus(TruckStatus status);

    List<Truck> findByStatusAndTruckType(TruckStatus status, TruckType type);

    Truck findByLicensePlate(String licensePlate);

    boolean existsByLicensePlate(String licensePlate);

    Truck findByVin(String vin);

    boolean existsByVin(String vin);

    List<Truck> findByTruckType(TruckType truckType);

    List<Truck> findByMaxLoadWeightGreaterThanEqual(Double weight);

    List<Truck> findByStatusAndMaxLoadWeightGreaterThanEqual(TruckStatus status, Double weight);
}
