package org.park_easy_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.park_easy_backend.dto.ParkingSpaceDTO;

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

    public static ParkingSpaceEntity toParkingSpaceEntity(ParkingSpaceDTO parkingSpaceDTO){
        ParkingSpaceEntity parkingSpaceEntity = new ParkingSpaceEntity();
        parkingSpaceEntity.setId(parkingSpaceDTO.getId());
        parkingSpaceEntity.setCityId(parkingSpaceDTO.getCityId());
        parkingSpaceEntity.setAvailability(parkingSpaceDTO.getAvailability());

        return parkingSpaceEntity;
    }

}
