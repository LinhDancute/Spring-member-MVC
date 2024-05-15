package com.example.springmembermvc.Mapper;

import com.example.springmembermvc.Model.DTO.device.deviceDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;
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
}