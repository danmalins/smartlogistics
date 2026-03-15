package org.dgroup.userservicesmartlogistics.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateManagerRequestDTO {

    String email;
    String password;

    String firstName;
    String lastName;
    String phone;

    String department;

    String employeeNumber;

}
