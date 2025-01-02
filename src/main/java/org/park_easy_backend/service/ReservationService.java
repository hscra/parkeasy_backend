package org.park_easy_backend.service;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.ParkingSpaceDTO;
import org.park_easy_backend.dto.ReservationDTO;
import org.park_easy_backend.entity.ParkingSpaceEntity;
import org.park_easy_backend.entity.ReservationEntity;
import org.park_easy_backend.repository.ParkingSpaceRepository;
import org.park_easy_backend.repository.ReservationRepository;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ParkingSpaceService parkingSpaceService;

    public List<ReservationDTO> findAll(){
        List<ReservationEntity> entities = reservationRepository.findAll();
        List<ReservationDTO> DTOs = new ArrayList<>();

        for(ReservationEntity entity : entities){
            DTOs.add(ReservationDTO.toReservationDTO(entity));
        }

        return DTOs;
    }

    public List<ReservationDTO> findAllReservationsByUserId(Long Id){
        List<ReservationEntity> entities = reservationRepository.findAll();
        List<ReservationDTO> DTOs = new ArrayList<>();

        for(ReservationEntity entity : entities) {
            if (entity.getUserId().equals(Id)) {
                DTOs.add(ReservationDTO.toReservationDTO(entity));
            }
        }

        return DTOs;
    }

    public List<ReservationDTO> findAllReservationsByCityId(Long Id){
        List<ReservationEntity> entities = reservationRepository.findAll();
        List<ReservationDTO> DTOs = new ArrayList<>();

        for(ReservationEntity entity : entities) {

            ParkingSpaceDTO parkingSpaceDTO = parkingSpaceService.findParkingSpaceEntityById(entity.getParkingSpaceId());

            if (parkingSpaceDTO != null && parkingSpaceDTO.getCity_Id().equals(Id)) {
                DTOs.add(ReservationDTO.toReservationDTO(entity));
            }
        }

        return DTOs;
    }

    public ReservationDTO findReservationEntityById(Long Id){
        Optional<ReservationEntity> result = reservationRepository.findById(Id);
        return result.map(ReservationDTO::toReservationDTO).orElse(null);
    }

    public void makeReservation(ReservationDTO DTO){

        Long spaceId = DTO.getReservationId();
        LocalDateTime start = DTO.getFrom();
        LocalDateTime end = DTO.getTo();

        Optional<List<ReservationEntity>> optionalReservationEntities = reservationRepository.findByParkingSpaceId(spaceId);

        if(optionalReservationEntities.isPresent()){
            List<ReservationEntity> reservations = optionalReservationEntities.get();

            for(ReservationEntity entity : reservations){
                if(isTimeOverlap(start, end, entity.getFrom(), entity.getTo())){
                    throw new IllegalArgumentException("Parking is unavailable during this timeframe");
                }
            }
        }

        ReservationEntity reservationEntity = ReservationEntity.toReservationEntity(DTO);
        reservationRepository.save(reservationEntity);
        parkingSpaceService.changeAvailability(spaceId, false);

    }

    private boolean isTimeOverlap(LocalDateTime newStart, LocalDateTime newEnd, LocalDateTime existingStart, LocalDateTime existingEnd){
        return newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }

    public void removeReservation(Long Id){
        Optional<ReservationEntity> entity = reservationRepository.findById(Id);

        if(entity.isPresent()){
            ReservationEntity reservationEntity = entity.get();
            parkingSpaceService.changeAvailability(reservationEntity.getReservationId(), true);
            reservationRepository.delete(reservationEntity);
        }
    }

}
