package org.park_easy_backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.ParkingSpaceDTO;
import org.park_easy_backend.dto.ReservationDTO;
import org.park_easy_backend.entity.MemberEntity;
import org.park_easy_backend.entity.ParkingSpaceEntity;
import org.park_easy_backend.entity.ReservationEntity;
import org.park_easy_backend.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final ParkingSpaceService parkingSpaceService;
    private final HistoricalReservationService historicalReservationService;
    private final MemberService memberService;

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

        for (ReservationEntity entity : entities) {
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
        Long spaceId = DTO.getParkingSpaceId();

        if (!parkingSpaceService.findParkingSpaceEntityById(spaceId).getAvailability()) {
            throw new IllegalArgumentException();
        } else {
            ReservationEntity reservationEntity = ReservationEntity.toReservationEntity(DTO);

            if (reservationEntity.getPoints() == null) {
                reservationEntity.setPoints(100L);
            }

            reservationRepository.save(reservationEntity);
            parkingSpaceService.changeAvailability(spaceId, false);
        }
    }

    public void removeReservation(Long Id){
        Optional<ReservationEntity> entity = reservationRepository.findById(Id);

        if (entity.isPresent()) {
            historicalReservationService.archiveReservation(Id);
            ReservationEntity reservationEntity = entity.get();
            parkingSpaceService.changeAvailability(reservationEntity.getParkingSpaceId(), true);
            reservationRepository.delete(reservationEntity);
        }
    }

    @Transactional
    public void updatePaymentStatus(Long Id, Integer newStatus){
        Optional<ReservationEntity> optionalReservationEntity = reservationRepository.findById(Id);
        if (optionalReservationEntity.isEmpty()) { return; }

        ReservationEntity reservationEntity = optionalReservationEntity.get();

        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(reservationEntity.getUserId());
        if (optionalMemberEntity.isEmpty()) { return; }

        MemberEntity memberEntity = optionalMemberEntity.get();

        reservationEntity.setPaymentStatus(newStatus);
        reservationRepository.save(reservationEntity);

        Long targetMemberPoints = memberEntity.getPoints() + reservationEntity.getPoints();
        memberEntity.setPoints(targetMemberPoints);

    }
}
