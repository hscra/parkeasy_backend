package org.park_easy_backend.dto;

import lombok.*;
import org.park_easy_backend.entity.ReservationEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReservationDTO {
    private Long reservationId;
    private Long userId;
    private Long parkingSpaceId;

    public static ReservationDTO toReservationDTO(ReservationEntity entity){
        ReservationDTO DTO = new ReservationDTO();
        DTO.setReservationId(entity.getReservationId());
        DTO.setUserId(entity.getUserId());
        DTO.setParkingSpaceId(entity.getParkingSpaceId());

        return DTO;
    }
}
