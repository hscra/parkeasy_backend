package org.park_easy_backend.service;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.LocationDTO;
import org.park_easy_backend.entity.LocationEntity;
import org.park_easy_backend.entity.ParkingSpaceEntity;
import org.park_easy_backend.repository.MapRepository;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;

    public void save(LocationDTO locationDTO){
        LocationEntity locationEntity = LocationEntity.toLocationEntity(locationDTO);

        if(locationDTO.getParkingSpaces() != null){
            List<ParkingSpaceEntity> parkingSpaces = new ArrayList<>();

            for(int i = 0; i < locationDTO.getParkingSpaces().size(); i++){
                ParkingSpaceEntity space = new ParkingSpaceEntity();
                space.setLocation(locationEntity);
                space.setSpaceNumber(i + 1);
                space.setAvailability(locationDTO.getParkingSpaces().get(i));
                parkingSpaces.add(space);
            }
            locationEntity.setParkingSpaces(parkingSpaces);
        }
        mapRepository.save(locationEntity);
    }

    public List<LocationDTO> findAll(){
        List<LocationEntity> locationEntities = mapRepository.findAll();
        List<LocationDTO> locationDTOs = new ArrayList<>();

        for(LocationEntity entity : locationEntities){
            locationDTOs.add(LocationDTO.toLocationDTO(entity));
        }
        return locationDTOs;
    }

    public LocationDTO findByCity(LocationDTO locationDTO){
        Optional<LocationEntity> byLocationName = mapRepository.findByCity(locationDTO.getCity());
        return byLocationName.map(LocationDTO::toLocationDTO).orElse(null);
    }
}
