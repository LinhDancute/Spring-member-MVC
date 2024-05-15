package com.example.springmembermvc.Repository;

import com.example.springmembermvc.Model.Entity.deviceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface deviceRepository extends JpaRepository<deviceEntity, Integer> {
    @Query("SELECT d FROM deviceEntity d WHERE CAST(d.id AS string) LIKE CONCAT(:prefix, '%')")
    Page<deviceEntity> findByMaTBStartingWith(@Param("prefix") Integer prefix, Pageable pageable);
    @Query("SELECT d FROM deviceEntity d WHERE d.TenTB LIKE %:query%")
    Page<deviceEntity> findByMaTBContaining(@Param("query") String query, Pageable pageable);
}
