package com.example.springmembermvc.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "xuly")
public class handle_violations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaXL", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaTV")
    private member MaTV;

    @Column(name = "SoTien")
    private Double SoTien;

    @Column(name = "TrangThaiXL")
    private Integer TrangThaiXL;

    @Column(name = "NgayXL")
    private Instant NgayXL;

    @Column(name = "HinhThucXL")
    private String HinhThucXL;

}