package org.dgroup.userservicesmartlogistics.dto.admin;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.dgroup.userservicesmartlogistics.model.UserRole;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRoleRequestDTO {
    UserRole role;
}
