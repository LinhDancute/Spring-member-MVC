package com.example.springmembermvc.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "thanhvien")
public class memberEntity {
    @Id
    @Column(name = "MaTV", nullable = false)
    private Integer id;

    @Column(name = "Email")
    private String email;

    @Column(name = "HoTen")
    private String HoTen;

    @Column(name = "Khoa")
    private String Khoa;

    @Column(name = "Nganh")
    private String Nganh;

    @Column(name = "Password")
    private String Password;

    @Column(name = "SDT")
    private String SDT;

    @OneToMany(mappedBy = "MaTV")
    private Set<usage_informationEntity> thongtinsds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "MaTV")
    private Set<handle_violationsEntity> xulies = new LinkedHashSet<>();

}