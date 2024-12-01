package org.park_easy_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.park_easy_backend.dto.LocationDTO;

@Entity
@Setter
@Getter
@Table(name = "locations")
public class LocationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String city;

    @Column
    private float lat;

    @Column
    private float lng;

    public static LocationEntity toLocationEntity(LocationDTO locationDTO) {
        LocationEntity locationEntity = new LocationEntity();
        locationEntity.setCity(locationDTO.getCity());
        locationEntity.setLat(locationDTO.getLat());
        locationEntity.setLng(locationDTO.getLng());
        return locationEntity;
    }
}
