package org.dgroup.userservicesmartlogistics.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientProfileResponseDTO {

    UUID id;

    String email;

    String firstName;
    String lastName;
    String phone;

    String companyName;
    String companyAddress;
    String taxNumber;

    LocalDateTime createdAt;

}
