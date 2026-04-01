package org.dgroup.userservicesmartlogistics.dto.manager;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateDriverRequestDTO {

    String email;
    String password;

    String firstName;
    String lastName;
    String phone;

    String driverLicenseNumber;
    String truckType;
    Double truckCapacityWeight;
    Double truckCapacityVolume;
}