package com.example.springmembermvc.Repository;


import com.example.springmembermvc.Model.member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface memberRespository extends JpaRepository<member, Integer> {
    @Query("SELECT m FROM member m WHERE m.MaTV = :MaTV")
    member findByMaTV(@Param("MaTV") int MaTV);

    @Query("SELECT m FROM member m WHERE m.MaTV = :MaTV AND m.Password = :Password")
    member findByMaTVandPassword(@Param("MaTV") int MaTV, @Param("Password") String Password);

    @Query("SELECT m from member m WHERE m.Email = :Email")
    member findByEmail(@Param("Email") String email);
}

