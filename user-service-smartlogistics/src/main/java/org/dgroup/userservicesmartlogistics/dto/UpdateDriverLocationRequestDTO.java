package org.dgroup.userservicesmartlogistics.dto;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateDriverLocationRequestDTO {
    Double latitude;
    Double longitude;
}
