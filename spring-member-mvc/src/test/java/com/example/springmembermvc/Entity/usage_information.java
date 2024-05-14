package com.example.springmembermvc.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "thongtinsd")
public class usage_information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTT", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaTB")
    private device MaTB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaTV")
    private member MaTV;

    @Column(name = "TGDatcho")
    private Instant TGDatcho;

    @Column(name = "TGMuon")
    private Instant TGMuon;

    @Column(name = "TGTra")
    private Instant TGTra;

    @Column(name = "TGVao")
    private Instant TGVao;

}