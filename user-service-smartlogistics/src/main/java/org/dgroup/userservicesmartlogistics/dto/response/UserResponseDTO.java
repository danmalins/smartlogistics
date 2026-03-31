package org.dgroup.userservicesmartlogistics.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDTO {

    UUID id;

    String email;

    String firstName;

    String lastName;

    String phone;

    LocalDateTime createdAt;
}