package org.dgroup.userservicesmartlogistics.dto.response;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverSearchResponseDTO {
    UUID driverId;

    String firstName;

    String lastName;

    String truckType;

    Double rating;

    DriverStatus status;
}
