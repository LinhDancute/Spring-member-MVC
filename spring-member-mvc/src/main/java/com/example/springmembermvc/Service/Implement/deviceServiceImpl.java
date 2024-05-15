package com.example.springmembermvc.Service.Implement;


import com.example.springmembermvc.Model.DTO.device.deviceDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;
import com.example.springmembermvc.Repository.deviceRepository;
import com.example.springmembermvc.Mapper.deviceMapper;
import com.example.springmembermvc.Service.deviceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class deviceServiceImpl implements deviceService {

    private final deviceRepository deviceRepository;
    private final deviceMapper deviceMapper;

    public deviceServiceImpl(deviceRepository deviceRepository, deviceMapper deviceMapper) {
        this.deviceRepository = deviceRepository;
        this.deviceMapper = deviceMapper;
    }

    @Override
    public List<deviceDTO> getAllDevices() {
        List<deviceEntity> devices = deviceRepository.findAll();
        return devices.stream()
                .map(deviceMapper::convertToDTO)
                .collect(Collectors.toList());
    }
}
