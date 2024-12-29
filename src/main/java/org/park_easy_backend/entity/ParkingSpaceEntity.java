package org.park_easy_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "parkingSpaces")
public class ParkingSpaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cityId", nullable = false)
    private LocationEntity cityId;

    @Column(name = "availability")
    private Boolean availability;

}
