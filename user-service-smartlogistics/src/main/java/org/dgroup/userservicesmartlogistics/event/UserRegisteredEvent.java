package org.dgroup.userservicesmartlogistics.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisteredEvent {
    String email;
    String firstName;
    String lastName;
}
