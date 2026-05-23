package org.dgroup.userservicesmartlogistics.dto.request.truck;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.dgroup.userservicesmartlogistics.model.TruckStatus;
import org.dgroup.userservicesmartlogistics.model.TruckType;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateTruckRequestDTO {

    String brand;
    String model;
    String licensePlate;
    String vin;
    Integer year;
    Double mileage;
    Double maxLoadWeight;
    Double cargoVolume;
    TruckType truckType;
    TruckStatus status;
}
