package com.example.springmembermvc.Mapper;

import com.example.springmembermvc.Model.DTO.device.deviceDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;
import com.example.springmembermvc.Model.Entity.memberEntity;
import org.springframework.stereotype.Component;

@Component
public class deviceMapper {
    public deviceDTO convertToDTO(deviceEntity deviceEntity) {
        deviceDTO dto = new deviceDTO();
        dto.setMaTB(deviceEntity.getId());
        dto.setTenTB(deviceEntity.getTenTB());
        dto.setMotaTB(deviceEntity.getMotaTB());
        dto.setMotaTB(deviceEntity.getHinhanhTB());
        return dto;
    }

    
    public deviceEntity toEntity(deviceDTO dto) {
        if (dto == null) {
            return null;
        }

        deviceEntity entity = new deviceEntity();
        entity.setId(dto.getMaTB());
        entity.setMotaTB(dto.getMotaTB());
        entity.setTenTB(dto.getTenTB());
        entity.setHinhanhTB(dto.getHinhanhTB());
        entity.setTrangThai(dto.getTrangThai());
        // If you have any mappings for sets or collections, you should handle them here

        return entity;
    }
}