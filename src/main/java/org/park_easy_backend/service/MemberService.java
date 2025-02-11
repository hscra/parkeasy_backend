package org.park_easy_backend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.MemberDTO;
import org.park_easy_backend.entity.MemberEntity;
import org.park_easy_backend.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void save(MemberDTO memberDTO) throws SQLException {
        memberDTO.setPassword(encoder.encode(memberDTO.getPassword()));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    @Transactional
    public void saveAll(List<MemberDTO> memberDTOList) throws SQLException {
        List<MemberEntity> memberEntities = memberDTOList.stream().map(dto -> {
            dto.setPassword(encoder.encode(dto.getPassword())); // Encrypt password for each member
            return MemberEntity.toMemberEntity(dto);
        }).toList();

        memberRepository.saveAll(memberEntities);
    }

    public MemberDTO login(MemberDTO memberDTO){
        Optional<MemberEntity> byMemberEmail = memberRepository.findByEmail(memberDTO.getEmail());

        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            if (encoder.matches(memberDTO.getPassword(), memberEntity.getPassword())) {
                return MemberDTO.toMemberDTORes(memberEntity);
            }
        }

        return null;
    }

//    @Transactional
//    public void addMemberPoints(Long Id, Long points){
//        Optional<MemberEntity> entity = memberRepository.findById(Id);
//
//        if(entity.isPresent()){
//            MemberEntity memberEntity = entity.get();
//            Long newPoints = memberEntity.getPoints() + points;
//            memberEntity.setPoints(newPoints);
//            memberRepository.save(memberEntity);
//        }
//    }
}
