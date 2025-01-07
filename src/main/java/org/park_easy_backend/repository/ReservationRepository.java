package org.park_easy_backend.repository;

import org.park_easy_backend.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    Optional<List<ReservationEntity>> findByParkingSpaceId(Long Id);
}
