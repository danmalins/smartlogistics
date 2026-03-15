package org.dgroup.userservicesmartlogistics.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverProfileResponseDTO {

    UUID id;

    String email;

    String firstName;
    String lastName;
    String phone;

    String driverLicenseNumber;

    String truckType;

    Double truckCapacityWeight;
    Double truckCapacityVolume;

    DriverStatus status;

    Double rating;

    Integer completedDeliveries;

}