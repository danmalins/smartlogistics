package org.dgroup.userservicesmartlogistics.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdatePasswordRequestDTO {
    String oldPassword;
    String newPassword;
}
