package com.example.springmembermvc.Repository;

import com.example.springmembermvc.Model.device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface device_repository extends JpaRepository<device, String> {
}
