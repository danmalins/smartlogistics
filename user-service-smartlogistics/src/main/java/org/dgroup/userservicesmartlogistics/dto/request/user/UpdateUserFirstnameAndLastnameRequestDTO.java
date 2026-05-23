package org.dgroup.userservicesmartlogistics.dto.request.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateUserFirstnameAndLastnameRequestDTO {
    String NewFirstname;
    String newLastname;
}
