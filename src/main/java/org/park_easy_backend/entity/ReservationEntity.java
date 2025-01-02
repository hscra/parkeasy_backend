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

    @JoinColumn(name = "userId", nullable = false)
    private Long userId;

    @Column(name = "start_time")
    private LocalDateTime from;

    @Column(name = "end_time")
    private LocalDateTime to;

    @JoinColumn(name = "parkingSpaceId", nullable = false)
    private Long parkingSpaceId;

    public static ReservationEntity toReservationEntity(ReservationDTO DTO){
        ReservationEntity entity = new ReservationEntity();
        entity.setReservationId(DTO.getReservationId());
        entity.setUserId(DTO.getUserId());
        entity.setFrom(DTO.getFrom());
        entity.setTo(DTO.getTo());
        entity.setParkingSpaceId(DTO.getParkingSpaceId());

        return entity;

    }
}
