package com.example.springmembermvc.Service;

import com.example.springmembermvc.Model.DTO.device.deviceDTO;

import java.util.List;

public interface deviceService {
    List<deviceDTO> getAllDevices();
    void saveDevice(deviceDTO device);
    deviceDTO getDeviceById(Integer id);
    void updateDevice(deviceDTO device);
    void deleteDevice(Integer id);
}
