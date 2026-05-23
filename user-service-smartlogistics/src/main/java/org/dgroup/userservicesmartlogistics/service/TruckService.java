package org.dgroup.userservicesmartlogistics.service;

import org.dgroup.userservicesmartlogistics.dto.request.truck.CreateTruckRequestDTO;
import org.dgroup.userservicesmartlogistics.model.Truck;
import org.dgroup.userservicesmartlogistics.model.TruckStatus;
import org.dgroup.userservicesmartlogistics.model.TruckType;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TruckService {

    Truck createTruck(CreateTruckRequestDTO request, Authentication authentication);
    Truck updateTruck(UUID truckId, UpdateTruckRequestDTO request);
    void deleteTruck(UUID truckId);
    void changeStatus(UUID truckId, TruckStatus status);
    void markAvailable(UUID truckId);
    void markInUse(UUID truckId);
    void sendToMaintenance(UUID truckId);
    Truck assignDriver(UUID truckId, UUID driverId);
    void unassignDriver(UUID truckId);
    List<Truck> findAvailableTrucks(BigDecimal requiredWeight);
    List<Truck> findAvailableTrucksByType(
            TruckType type,
            BigDecimal requiredWeight
    );
    void updateMileage(UUID truckId, BigDecimal distance);
    BigDecimal getMileage(UUID truckId);
    Truck getTruckById(UUID truckId);
    List<Truck> getAllTrucks();
    List<Truck> getTrucksByStatus(TruckStatus status);
    void validateTruckCanBeAssigned(UUID truckId);
}
