package org.park_easy_backend.service;

import org.park_easy_backend.dto.LocationDTO;
import org.park_easy_backend.dto.MemberDTO;
import org.park_easy_backend.entity.LocationEntity;
import org.park_easy_backend.entity.MemberEntity;
import org.park_easy_backend.repository.MapRepository;
import org.park_easy_backend.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.xml.stream.Location;
import java.util.Optional;

public class MapService {
    private final MapRepository mapRepository;

    public void save(LocationDTO locationDTO){
        LocationEntity locationEntity = LocationEntity.toLocationEntity(locationDTO);
        MapRepository.save(locationEntity);
    }

    public LocationDTO find(LocationDTO locationDTO){
        Optional<LocationEntity> byLocationName = MapRepository.findByName(locationDTO.getCity());
        return byLocationName.map(LocationDTO::toLocationDTO).orElse(null);
    }
}
