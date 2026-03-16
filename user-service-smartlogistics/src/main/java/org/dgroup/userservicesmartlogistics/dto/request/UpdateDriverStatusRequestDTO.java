package org.dgroup.userservicesmartlogistics.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.dgroup.userservicesmartlogistics.model.DriverStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDriverStatusRequestDTO {
    DriverStatus driverStatus;
}
