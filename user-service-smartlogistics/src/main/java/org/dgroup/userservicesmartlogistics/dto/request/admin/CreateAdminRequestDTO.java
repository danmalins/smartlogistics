package org.dgroup.userservicesmartlogistics.dto.request.admin;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAdminRequestDTO {

    String email;

    String password;

    String firstName;

    String lastName;

    String phone;
}
