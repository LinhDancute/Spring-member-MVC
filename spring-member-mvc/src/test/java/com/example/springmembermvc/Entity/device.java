package com.example.springmembermvc.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "thietbi")
public class device {
    @Id
    @Column(name = "MaTB", nullable = false)
    private Integer id;

    @Column(name = "MotaTB")
    private String MotaTB;

    @Column(name = "TenTB")
    private String TenTB;

    @OneToMany(mappedBy = "maTB")
    private Set<usage_information> thongtinsds = new LinkedHashSet<>();

}