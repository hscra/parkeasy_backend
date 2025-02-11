package org.park_easy_backend.service;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.ParkingSpaceDTO;
import org.park_easy_backend.dto.ReservationDTO;
import org.park_easy_backend.entity.HistoricalReservationEntity;
import org.park_easy_backend.entity.ReservationEntity;
import org.park_easy_backend.repository.HistoricalReservationRepository;
import org.park_easy_backend.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HistoricalReservationService {
    private final HistoricalReservationRepository historicalReservationRepository;
    private final ReservationRepository reservationRepository;
    private final ParkingSpaceService parkingSpaceService;

    public List<ReservationDTO> findAll(){
        List<HistoricalReservationEntity> entities = historicalReservationRepository.findAll();
        List<ReservationDTO> DTOs = new ArrayList<>();

        for(HistoricalReservationEntity entity : entities){
            DTOs.add(ReservationDTO.toReservationDTO(entity));
        }

        return DTOs;
    }

    public List<ReservationDTO> findAllReservationsByUserId(Long Id){
        List<HistoricalReservationEntity> entities = historicalReservationRepository.findAll();
        List<ReservationDTO> DTOs = new ArrayList<>();

        for(HistoricalReservationEntity entity : entities) {
            if (entity.getUserId().equals(Id)) {
                DTOs.add(ReservationDTO.toReservationDTO(entity));
            }
        }

        return DTOs;
    }

    public List<ReservationDTO> findAllReservationsByCityId(Long Id){
        List<HistoricalReservationEntity> entities = historicalReservationRepository.findAll();
        List<ReservationDTO> DTOs = new ArrayList<>();

        for (HistoricalReservationEntity entity : entities) {
            ParkingSpaceDTO parkingSpaceDTO = parkingSpaceService.findParkingSpaceEntityById(entity.getParkingSpaceId());

            if (parkingSpaceDTO != null && parkingSpaceDTO.getCity_Id().equals(Id)) {
                DTOs.add(ReservationDTO.toReservationDTO(entity));
            }
        }

        return DTOs;
    }

    public ReservationDTO findReservationEntityById(Long Id){
        Optional<HistoricalReservationEntity> result = historicalReservationRepository.findById(Id);
        return result.map(ReservationDTO::toReservationDTO).orElse(null);
    }

    public void archiveReservation(Long Id){
        Optional<ReservationEntity> optionalReservationEntity = reservationRepository.findById(Id);
        if (optionalReservationEntity.isEmpty()) { return; }

        ReservationDTO reservationDTO = ReservationDTO.toReservationDTO(optionalReservationEntity.get());
        HistoricalReservationEntity historicalReservationEntity = HistoricalReservationEntity.toReservationEntity(reservationDTO);
        historicalReservationEntity.setPaymentStatus(0);
        historicalReservationRepository.save(historicalReservationEntity);
    }
}