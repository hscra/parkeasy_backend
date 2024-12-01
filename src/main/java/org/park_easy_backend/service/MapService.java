package org.park_easy_backend.service;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.LocationDTO;
import org.park_easy_backend.entity.LocationEntity;
import org.park_easy_backend.repository.MapRepository;
import org.springframework.stereotype.Service;

import javax.xml.stream.Location;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MapService {
    private final MapRepository mapRepository;

    public void save(LocationDTO locationDTO){
        LocationEntity locationEntity = LocationEntity.toLocationEntity(locationDTO);
        mapRepository.save(locationEntity);
    }

    public LocationDTO find(LocationDTO locationDTO){
        Optional<LocationEntity> byLocationName = mapRepository.findLocationEntityByCity(locationDTO.getCity());
        return byLocationName.map(LocationDTO::toLocationDTO).orElse(null);
    }
}
