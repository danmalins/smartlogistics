package org.dgroup.userservicesmartlogistics.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
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
    @JoinColumn(name = "user_email", nullable = false, unique = true)
    User user;

    @Column(nullable = false)
    String driverLicenseNumber;

    @Enumerated(EnumType.STRING)
    DriverStatus status;

    @Column(nullable = false)
    BigDecimal currentLatitude;

    @Column(nullable = false)
    BigDecimal currentLongitude;

    @Column(nullable = false)
    Double rating;

    @Column(nullable = false)
    Integer completedDeliveries;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "truck_id")
    private Truck truck;
}
