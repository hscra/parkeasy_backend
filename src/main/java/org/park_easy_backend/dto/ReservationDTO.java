package org.park_easy_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.park_easy_backend.entity.HistoricalReservationEntity;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endTime;
    private Integer paymentStatus;
    private Long points;

    public static ReservationDTO toReservationDTO(ReservationEntity entity){
        ReservationDTO DTO = new ReservationDTO();
        DTO.setReservationId(entity.getReservationId());
        DTO.setUserId(entity.getUserId());
        DTO.setParkingSpaceId(entity.getParkingSpaceId());
        DTO.setPaymentStatus(entity.getPaymentStatus());
        DTO.setStartTime(entity.getStartTime());
        DTO.setEndTime(entity.getEndTime());
        DTO.setPoints(entity.getPoints());

        return DTO;
    }

    public static ReservationDTO toReservationDTO(HistoricalReservationEntity entity){
        ReservationDTO DTO = new ReservationDTO();
        DTO.setReservationId(entity.getReservationId());
        DTO.setUserId(entity.getUserId());
        DTO.setParkingSpaceId(entity.getParkingSpaceId());
        DTO.setPaymentStatus(entity.getPaymentStatus());
        DTO.setStartTime(entity.getStartTime());
        DTO.setEndTime(entity.getEndTime());
        DTO.setPoints(entity.getPoints());

        return DTO;
    }
}
