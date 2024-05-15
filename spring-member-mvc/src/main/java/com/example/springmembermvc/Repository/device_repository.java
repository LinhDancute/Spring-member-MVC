package com.example.springmembermvc.Repository;

import com.example.springmembermvc.Model.device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface device_repository extends JpaRepository<device, String> {
    @Query("SELECT d FROM device d WHERE d.MaTB LIKE :prefix%")
    Page<device> findByMaTBStartingWith(@Param("prefix") String prefix, Pageable pageable);
    @Query("SELECT d FROM device d WHERE d.TenTB LIKE %:query%")
    Page<device> findByMaTBContaining(@Param("query") String query, Pageable pageable);
}
