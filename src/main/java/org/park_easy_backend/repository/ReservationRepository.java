package org.park_easy_backend.repository;

import org.park_easy_backend.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

}
