package org.park_easy_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "parking_spaces")
public class ParkingSpaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private LocationEntity location;

    @Column(name = "space_number")
    private Integer spaceNumber;

    @Column(name = "availability")
    private Boolean availability;

}
