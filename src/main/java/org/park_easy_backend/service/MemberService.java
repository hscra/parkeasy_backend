package org.park_easy_backend.service;

import lombok.RequiredArgsConstructor;
import org.park_easy_backend.dto.MemberDTO;
import org.park_easy_backend.entity.MemberEntity;
import org.park_easy_backend.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void save(MemberDTO memberDTO){
        memberDTO.setMemberPassword(encoder.encode(memberDTO.getMemberPassword()));
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public MemberDTO login(MemberDTO memberDTO){
        /*
            1. check email form DB query
            2. check the password match between input and DB

         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            System.out.println(memberEntity);
            return MemberDTO.toMemberDTO(memberEntity);
//            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
//                // entity -> dto
//                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
//                System.out.println("git");
//                return dto;
//            } else {
//                return null;
//            }
        } else {
            return null;
        }
    }
}
