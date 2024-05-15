package com.example.springmembermvc.Repository;


import com.example.springmembermvc.Model.Entity.memberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface memberRespository extends JpaRepository<memberEntity, Integer> {
//    @Query("SELECT m FROM member m WHERE m.MaTV = :MaTV")
//    member findByMaTV(@Param("MaTV") int MaTV);
//
//    @Query("SELECT m FROM member m WHERE m.MaTV = :MaTV AND m.Password = :Password")
//    member findByMaTVandPassword(@Param("MaTV") int MaTV, @Param("Password") String Password);

//    @Query("SELECT m from member m WHERE m.Email = :Email")
//    boolean findByEmail(@Param("Email") String email);

//    @Query("SELECT m from memberEntity m WHERE m.Email = :Email")
//    boolean existsByEmail(@Param("Email") String Email);

    boolean existsByEmail(String Email);

    @Query("SELECT DISTINCT m.Khoa FROM memberEntity m")
    List<String> findAllDepartments();
}

