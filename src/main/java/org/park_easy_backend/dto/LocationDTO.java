package org.park_easy_backend.dto;

import lombok.*;
import org.park_easy_backend.entity.LocationEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LocationDTO {
    private Long id;
    private String city;
    private float Lat;
    private float Lng;

    public static LocationDTO toLocationDTO(LocationEntity locationEntity) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(locationEntity.getId());
        locationDTO.setCity(locationEntity.getCity());
        locationDTO.setLat(locationEntity.getLat());
        locationDTO.setLng(locationEntity.getLng());
        return locationDTO;
    }

}
