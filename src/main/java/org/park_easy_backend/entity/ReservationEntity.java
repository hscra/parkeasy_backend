package org.park_easy_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.park_easy_backend.dto.ReservationDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "reservations")
public class ReservationEntity {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "parking_space_id", nullable = false)
    private Long parkingSpaceId;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    public static ReservationEntity toReservationEntity(ReservationDTO DTO){
        ReservationEntity entity = new ReservationEntity();
        entity.setReservationId(DTO.getReservationId());
        entity.setUserId(DTO.getUserId());
        entity.setParkingSpaceId(DTO.getParkingSpaceId());
        entity.setStartTime(DTO.getStartTime());
        entity.setEndTime(DTO.getEndTime());

        return entity;
    }
}
