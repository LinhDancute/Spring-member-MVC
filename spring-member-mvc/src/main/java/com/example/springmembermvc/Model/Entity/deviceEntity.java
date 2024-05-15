package com.example.springmembermvc.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "thietbi")
public class deviceEntity {
    @Id
    @Column(name = "MaTB", nullable = false)
    private Integer id;

    @Column(name = "MotaTB")
    private String MotaTB;

    @Column(name = "TenTB")
    private String TenTB;

    @Column(name = "HinhanhTB")
    private String HinhanhTB;

    @Column(name = "TrangThai")
    private int TrangThai;

    @OneToMany(mappedBy = "MaTB")
    private Set<usage_informationEntity> thongtinsds = new LinkedHashSet<>();
}