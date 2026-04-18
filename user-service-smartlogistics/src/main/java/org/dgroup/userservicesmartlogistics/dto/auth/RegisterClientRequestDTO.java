package org.dgroup.userservicesmartlogistics.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterClientRequestDTO {
    @Email(message = "Invalid email format")
    @NotBlank
    private String email;
    private String password;

    private String firstName;
    private String lastName;
    private String phone;

    private String companyName;
    private String companyAddress;
    private String taxNumber;

}