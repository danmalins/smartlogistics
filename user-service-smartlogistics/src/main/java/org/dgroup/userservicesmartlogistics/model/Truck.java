package org.dgroup.userservicesmartlogistics.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "trucks")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Truck {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(nullable = false, unique = true)
    private String vin;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Double mileage;

    @Column(nullable = false)
    private Double maxLoadWeight;

    @Column(nullable = false)
    private Double cargoVolume;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TruckType truckType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TruckStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}