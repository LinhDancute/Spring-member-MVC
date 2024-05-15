package com.example.springmembermvc.Mapper;

import com.example.springmembermvc.Model.DTO.member.memberLoginDTO;
import com.example.springmembermvc.Model.DTO.member.memberRegisterDTO;
import com.example.springmembermvc.Model.DTO.member.memberDTO;
import com.example.springmembermvc.Model.Entity.memberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class memberMapper {
    private final PasswordEncoder passwordEncoder;

    public memberMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public memberEntity convertToEntity_Register(memberRegisterDTO memberRegisterDTO) {
        memberEntity entity = new memberEntity();
        entity.setId(memberRegisterDTO.getMaTV());
        entity.setEmail(memberRegisterDTO.getEmail());
        entity.setHoTen(memberRegisterDTO.getHoTen());
        return entity;
    }

    public memberEntity convertToEntity_Login(memberLoginDTO memberLoginDTO) {
        memberEntity entity = new memberEntity();
        entity.setId(memberLoginDTO.getMaTV());
        String encryptedPassword = passwordEncoder.encode(memberLoginDTO.getPassword());
        entity.setPassword(encryptedPassword);
        return entity;
    }


    public memberDTO convertToDTO(memberEntity memberEntity) {
        memberDTO dto = new memberDTO();

        dto.setMaTV(memberEntity.getId());
        dto.setHoTen(memberEntity.getHoTen());
        dto.setKhoa(memberEntity.getKhoa());
        dto.setNganh(memberEntity.getNganh());
        dto.setSDT(memberEntity.getSDT());
        dto.setPassword(memberEntity.getPassword());
        dto.setEmail(memberEntity.getEmail());
        return dto;
    }
}

