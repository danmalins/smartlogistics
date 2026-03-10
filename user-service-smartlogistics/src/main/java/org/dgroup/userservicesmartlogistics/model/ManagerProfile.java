package org.dgroup.userservicesmartlogistics.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "manager_profiles")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerProfile {

    @Id
    @GeneratedValue
    UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    User user;

    @Column(nullable = false)
    String department;

    @Column(nullable = false)
    String employeeNumber;

    @Column(nullable = false)
    LocalDateTime createdAt;

}