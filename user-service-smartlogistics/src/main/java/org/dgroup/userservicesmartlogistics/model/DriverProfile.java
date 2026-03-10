package org.dgroup.userservicesmartlogistics.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "driver_profiles")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DriverProfile {

    @Id
    @GeneratedValue
    UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    User user;

    @Column(nullable = false)
    String driverLicenseNumber;

    @Column(nullable = false)
    String truckType;

    @Column(nullable = false)
    Double truckCapacityWeight;

    @Column(nullable = false)
    Double truckCapacityVolume;

    @Enumerated(EnumType.STRING)
    DriverStatus status;

    @Column(nullable = false)
    Double currentLatitude;

    @Column(nullable = false)
    Double currentLongitude;

    @Column(nullable = false)
    Double rating;

    @Column(nullable = false)
    Integer completedDeliveries;

    @Column(nullable = false)
    LocalDateTime createdAt;

}
