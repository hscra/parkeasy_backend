package org.park_easy_backend.repository;

import org.park_easy_backend.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
    Optional<MemberEntity> findByEmail(String memberEmail);
    Optional<MemberEntity> findByName(String name);
}
