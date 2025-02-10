package org.park_easy_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.park_easy_backend.dto.ParkingSpaceDTO;

@Entity
@Setter
@Getter
@Table(name = "parking_spaces")
public class ParkingSpaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @JoinColumn(name = "city_Id", nullable = false)
    private Long city_Id;

    @Column(name = "availability")
    private Boolean availability;

    @Column(name = "price", nullable = false)
    private Double price;

    public static ParkingSpaceEntity toParkingSpaceEntity(ParkingSpaceDTO parkingSpaceDTO){
        ParkingSpaceEntity parkingSpaceEntity = new ParkingSpaceEntity();
        parkingSpaceEntity.setId(parkingSpaceDTO.getId());
        parkingSpaceEntity.setCity_Id(parkingSpaceDTO.getCity_Id());
        parkingSpaceEntity.setAvailability(parkingSpaceDTO.getAvailability());
        parkingSpaceEntity.setPrice(parkingSpaceDTO.getPrice());

        return parkingSpaceEntity;
    }

}
