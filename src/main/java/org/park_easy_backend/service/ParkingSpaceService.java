package org.park_easy_backend.service;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.ParkingSpaceDTO;
import org.park_easy_backend.entity.ParkingSpaceEntity;
import org.park_easy_backend.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingSpaceService {
    private final ParkingSpaceRepository parkingSpaceRepository;

    public void save(ParkingSpaceDTO parkingSpaceDTO){
        ParkingSpaceEntity parkingSpaceEntity = ParkingSpaceEntity.toParkingSpaceEntity(parkingSpaceDTO);
        parkingSpaceRepository.save(parkingSpaceEntity);
    }

    public List<ParkingSpaceDTO> findAll(){
        List<ParkingSpaceEntity> parkingSpaceEntities = parkingSpaceRepository.findAll();
        List<ParkingSpaceDTO> parkingSpaceDTOs = new ArrayList<>();

        for(ParkingSpaceEntity entity : parkingSpaceEntities){
            parkingSpaceDTOs.add(ParkingSpaceDTO.toParkingSpaceDTO(entity));
        }

        return parkingSpaceDTOs;
    }

    public ParkingSpaceDTO findParkingSpaceEntityById(Long id){
        Optional<ParkingSpaceEntity> byParkingSpaceId = parkingSpaceRepository.findById(id);
        return byParkingSpaceId.map(ParkingSpaceDTO::toParkingSpaceDTO).orElse(null);
    }

    public List<ParkingSpaceDTO> findAllByCityId(Long Id){
        List<ParkingSpaceEntity> parkingSpaceEntities = parkingSpaceRepository.findAll();
        List<ParkingSpaceDTO> parkingSpaceDTOS = new ArrayList<>();

        for(ParkingSpaceEntity entity : parkingSpaceEntities){
            if(entity.getCityId().equals(Id)) {
                parkingSpaceDTOS.add(ParkingSpaceDTO.toParkingSpaceDTO(entity));
            }
        }

        return parkingSpaceDTOS;
    }
}
