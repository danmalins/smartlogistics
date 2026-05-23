package org.dgroup.userservicesmartlogistics.dto.request.driver;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDriverLocationRequestDTO {
    BigDecimal latitude;
    BigDecimal longitude;
}
