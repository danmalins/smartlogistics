package org.dgroup.userservicesmartlogistics.dto.admin;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;


@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlockUserRequestDTO {
    UUID userId;
}
