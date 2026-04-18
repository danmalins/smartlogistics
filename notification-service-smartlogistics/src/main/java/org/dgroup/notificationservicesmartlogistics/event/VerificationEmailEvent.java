package org.dgroup.notificationservicesmartlogistics.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VerificationEmailEvent {
    String email;
    String firstName;
    String lastName;
    String token;
    // frontend/baseUrl field
    String verificationUrl; // optional
}
