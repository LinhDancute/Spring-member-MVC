package com.example.springmembermvc.Repository;


import com.example.springmembermvc.Model.member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface member_responsitory extends JpaRepository<member, Integer> {
    @Query("SELECT m FROM member m WHERE m.MaTV = :MaTV")
    member findByMSSV(@Param("MaTV") String MaTV);
}

