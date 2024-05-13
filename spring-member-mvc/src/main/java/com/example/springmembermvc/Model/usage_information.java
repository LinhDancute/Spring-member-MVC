package com.example.springmembermvc.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import java.util.Date;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "thongtinsd")
public class usage_information {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTT")
    public int MaTT;
    
    @Column(name = "TGVao")
    public LocalDateTime TGVao;
    
    @Column(name = "TGMuon", nullable = true)
    public LocalDateTime TGMuon;
    
    @Column(name = "TGTra", nullable = true)
    public LocalDateTime TGTra;
    
    @Column(name = "TGDatcho", nullable = true)
    public Date TGDatcho;

    @ManyToOne
    @JoinColumn(name = "MaTB", nullable = true)
    private device thietbi;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "MaTV")
    private member thanhvien;
    
    public usage_information(int MaTT, member thanhvien, device thietbi, LocalDateTime TGVao, LocalDateTime TGMuon, LocalDateTime TGTra, Date TGDatcho) {
        this.MaTT = MaTT;
        this.thanhvien = thanhvien;
        this.thietbi = thietbi;
        this.TGVao = TGVao;
        this.TGMuon = TGMuon;
        this.TGTra = TGTra;
        this.TGDatcho = TGDatcho;
    }

    public usage_information(int MaTT, member thanhvien, LocalDateTime TGVao) {
        this.MaTT = MaTT;
        this.thanhvien = thanhvien;
        this.TGVao = TGVao;
    }
    
    public usage_information() {
    }

    public int getMaTT() {
        return MaTT;
    }

    public void setMaTT(int maTT) {
        MaTT = maTT;
    }

    public LocalDateTime getTGVao() {
        return TGVao;
    }

    public void setTGVao(LocalDateTime TGVao) {
        this.TGVao = TGVao;
    }

    public LocalDateTime getTGMuon() {
        return TGMuon;
    }

    public void setTGMuon(LocalDateTime TGMuon) {
        this.TGMuon = TGMuon;
    }

    public LocalDateTime getTGTra() {
        return TGTra;
    }

    public void setTGTra(LocalDateTime TGTra) {
        this.TGTra = TGTra;
    }

    public Date getTGDatcho() {
        return TGDatcho;
    }

    public void setTGDatcho(Date TGDatcho) {
        this.TGDatcho = TGDatcho;
    }

    public device getThietbi() {
        return thietbi;
    }

    public void setThietbi(device thietbi) {
        this.thietbi = thietbi;
    }

    public member getThanhvien() {
        return thanhvien;
    }

    public void setThanhvien(member thanhvien) {
        this.thanhvien = thanhvien;
    }
}
