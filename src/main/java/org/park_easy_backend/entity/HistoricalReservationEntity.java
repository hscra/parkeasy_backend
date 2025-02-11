package org.park_easy_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.park_easy_backend.dto.ReservationDTO;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "historical_reservations")
public class HistoricalReservationEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "parking_space_id", nullable = false)
    private Long parkingSpaceId;

    @Column(name = "start_time", nullable = true)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = true)
    private LocalDateTime endTime;

    @Column(name = "payment_status", nullable = true)
    private Integer paymentStatus;

    @Column(name = "points", nullable = true)
    private Long points;

    public static HistoricalReservationEntity toReservationEntity(ReservationDTO DTO){
        HistoricalReservationEntity entity = new HistoricalReservationEntity();
        entity.setReservationId(DTO.getReservationId());
        entity.setUserId(DTO.getUserId());
        entity.setParkingSpaceId(DTO.getParkingSpaceId());
        entity.setPaymentStatus(DTO.getPaymentStatus());
        entity.setStartTime(DTO.getStartTime());
        entity.setEndTime(DTO.getEndTime());
        entity.setPoints(DTO.getPoints());

        return entity;
    }
}
