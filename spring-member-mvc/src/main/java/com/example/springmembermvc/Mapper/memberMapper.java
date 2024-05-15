package com.example.springmembermvc.Mapper;

import com.example.springmembermvc.Model.DTO.member.memberRegisterDTO;
import com.example.springmembermvc.Model.DTO.member.memberDTO;
import com.example.springmembermvc.Model.Entity.memberEntity;
import org.springframework.stereotype.Component;

@Component
public class memberMapper {
    public memberEntity convertToEntity(memberRegisterDTO memberRegisterDTO) {
        memberEntity entity = new memberEntity();
        entity.setId(memberRegisterDTO.getMaTV());
        entity.setEmail(memberRegisterDTO.getEmail());
        entity.setHoTen(memberRegisterDTO.getHoTen());
        return entity;
    }

    public memberDTO convertToDTO(memberEntity memberEntity) {
        memberDTO dto = new memberDTO();
        return dto;
    }
}

