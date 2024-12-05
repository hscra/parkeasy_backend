package org.park_easy_backend.dto;

import lombok.*;
import org.park_easy_backend.entity.LocationEntity;
import org.park_easy_backend.entity.ParkingSpaceEntity;

import java.util.List;
import java.util.stream.Collectors;

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
    private List<Boolean> parkingSpaces;

    public static LocationDTO toLocationDTO(LocationEntity locationEntity) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(locationEntity.getId());
        locationDTO.setCity(locationEntity.getCity());
        locationDTO.setLat(locationEntity.getLat());
        locationDTO.setLng(locationEntity.getLng());
        if(locationEntity.getParkingSpaces() != null){
            locationDTO.setParkingSpaces(locationEntity.getParkingSpaces().stream()
                    .map(ParkingSpaceEntity::getAvailability)
                    .collect(Collectors.toList()));
        }
        return locationDTO;
    }

}
