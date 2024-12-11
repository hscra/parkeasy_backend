package org.park_easy_backend.repository;

import org.park_easy_backend.entity.ParkingSpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpaceEntity, Long> {
    List<ParkingSpaceEntity> findByLocationId(Long locationId);
}
