package com.example.springmembermvc.Service;

import com.example.springmembermvc.Model.DTO.device.deviceDTO;
import com.example.springmembermvc.Model.Entity.deviceEntity;

import java.util.List;
import java.util.Optional;

public interface deviceService {
    List<deviceDTO> getAllDevices();


}
