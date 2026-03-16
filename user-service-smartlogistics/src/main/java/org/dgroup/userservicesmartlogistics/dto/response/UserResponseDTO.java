package org.dgroup.userservicesmartlogistics.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.dgroup.userservicesmartlogistics.model.UserRole;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO {

    UUID id;

    String email;

    String firstName;
    String lastName;

    String phone;

    UserRole role;

    boolean enabled;

}