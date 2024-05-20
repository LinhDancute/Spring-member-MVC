package com.example.springmembermvc.Mapper;

import com.example.springmembermvc.Model.DTO.usega_information.usage_informationDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;
import com.example.springmembermvc.Model.Entity.usage_informationEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;

public class usage_informationMapper {

    private final deviceMapper deviceMapper;

    private final memberMapper memberMapper;

    public usage_informationMapper(com.example.springmembermvc.Mapper.deviceMapper deviceMapper, com.example.springmembermvc.Mapper.memberMapper memberMapper) {
        this.deviceMapper = deviceMapper;
        this.memberMapper = memberMapper;
    }

    public usage_informationDTO converToDTO(usage_informationEntity usage_informationEntity) {
        usage_informationDTO dto = new usage_informationDTO();

        dto.setMaTT(usage_informationEntity.getId());

        dto.setThietbi(deviceMapper.convertToDTO(usage_informationEntity.getMaTB()));
        dto.setThanhvien(memberMapper.convertToDTO(usage_informationEntity.getMaTV()));

        dto.setTGTra(LocalDateTime.from(usage_informationEntity.getTGTra()));
        dto.setTGMuon(LocalDateTime.from(usage_informationEntity.getTGMuon()));
        dto.setTGDatcho(Date.from(usage_informationEntity.getTGDatcho()));

        return dto;
    }
}
