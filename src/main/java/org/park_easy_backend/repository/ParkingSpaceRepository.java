package org.park_easy_backend.repository;

import org.park_easy_backend.entity.ParkingSpaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ParkingSpaceRepository extends JpaRepository<ParkingSpaceEntity, Long> {
}
