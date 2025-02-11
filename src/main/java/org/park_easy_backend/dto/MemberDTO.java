package org.park_easy_backend.dto;

import lombok.*;
import org.park_easy_backend.entity.MemberEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberDTO {
    private Long id;

    private String email;

//    @JsonIgnore
    private String password;

    private String name;
    private Long points;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setPassword(memberEntity.getPassword());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setPoints(memberEntity.getPoints());
        return memberDTO;
    }

    public static MemberDTO toMemberDTORes(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setPoints(memberEntity.getPoints());
        return memberDTO;
    }
}
