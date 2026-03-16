package org.dgroup.userservicesmartlogistics.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ManagerProfileResponseDTO {

    UUID id;

    String email;

    String firstName;
    String lastName;
    String phone;

    String department;

    String employeeNumber;

    LocalDateTime createdAt;

}
