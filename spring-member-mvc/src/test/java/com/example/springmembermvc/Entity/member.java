package com.example.springmembermvc.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "thanhvien")
public class member {
    @Id
    @Column(name = "MaTV", nullable = false)
    private Integer id;

    @Column(name = "Email")
    private String Email;

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

    @OneToMany(mappedBy = "maTV")
    private Set<usage_information> thongtinsds = new LinkedHashSet<>();

    @OneToMany(mappedBy = "maTV")
    private Set<handle_violations> xulies = new LinkedHashSet<>();

}