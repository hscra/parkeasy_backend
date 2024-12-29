package org.park_easy_backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.park_easy_backend.entity.LocationEntity;
import org.park_easy_backend.entity.ParkingSpaceEntity;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParkingSpaceDTO {
    private Long id;
    private LocationEntity cityId;
    private Boolean availability;

    public static ParkingSpaceDTO toParkingSpaceEntity(ParkingSpaceDTO parkingSpaceDTO) {
        ParkingSpaceEntity parkingSpaceEntity = new ParkingSpaceEntity();
        parkingSpaceEntity.setId(parkingSpaceDTO.getId());
        parkingSpaceEntity.setCityId(parkingSpaceDTO.getCityId());
        parkingSpaceEntity.setAvailability(parkingSpaceDTO.getAvailability());
        return parkingSpaceDTO;
    }
}
