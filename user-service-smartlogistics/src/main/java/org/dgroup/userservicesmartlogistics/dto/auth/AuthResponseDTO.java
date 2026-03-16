package org.dgroup.userservicesmartlogistics.dto.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.dgroup.userservicesmartlogistics.model.UserRole;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponseDTO {

    private String token;
    private String email;
    private UserRole role;

}