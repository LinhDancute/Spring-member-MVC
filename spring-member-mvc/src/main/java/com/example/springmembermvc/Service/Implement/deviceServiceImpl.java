package com.example.springmembermvc.Service.Implement;


import com.example.springmembermvc.Model.DTO.device.deviceDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;
import com.example.springmembermvc.Repository.deviceRepository;
import com.example.springmembermvc.Mapper.deviceMapper;
import com.example.springmembermvc.Service.deviceService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public void saveDevice(deviceDTO device) {
        // Chuyển đổi từ DTO sang entity nếu cần
        deviceEntity entity = deviceMapper.convertToEntity(device);

        // Kiểm tra các trường bắt buộc
        if (entity.getTenTB() == null || entity.getTenTB().isEmpty()) {
            throw new IllegalArgumentException("Tên thiết bị không được để trống");
        }

        // Lưu entity vào cơ sở dữ liệu thông qua repository
        deviceRepository.save(entity);
    }

    @Autowired
    private deviceRepository device_repository;

    @Autowired
    private deviceMapper device_mapper;

    @Override
    public deviceDTO getDeviceById(Integer id) {
        deviceEntity entity = device_repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid device ID"));
        return device_mapper.convertToDTO(entity);
    }

    @Override
    public void updateDevice(deviceDTO device) {
        deviceEntity entity = device_repository.findById(device.getMaTB()).orElseThrow(() -> new IllegalArgumentException("Invalid device ID"));
        entity.setTenTB(device.getTenTB());
        entity.setMotaTB(device.getMotaTB());
        entity.setHinhanhTB(device.getHinhanhTB());
        device_repository.save(entity);
    }
    @Override
    public void deleteDevice(Integer id) {
        device_repository.deleteById(id);
    }
}
