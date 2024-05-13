package com.example.springmembermvc.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.Date;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "xuly")
public class handle_violations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaXL")
    public int MaXL;
    
    @Column(name = "HinhThucXL")
    public String HinhThucXL;
    
    @Column(name = "SoTien", nullable = true)
    public double SoTien;
    
    @Column(name = "NgayXL")
    public Date NgayXL;
    
    @Column(name = "TrangThaiXL")
    public int TrangThaiXL;

    @ManyToOne
    @JoinColumn(name = "MaTV")
    private member thanhvien;
    
    
    public handle_violations(int MaXL, int MaTV, String HinhThucXL, double SoTien, Date NgayXL, int TrangThaiXL) {
        this.MaXL = MaXL;
        this.HinhThucXL = HinhThucXL;
        this.SoTien = SoTien;
        this.NgayXL = NgayXL;
        this.TrangThaiXL = TrangThaiXL;
    }
    
    public handle_violations(member thanhvien, String HinhThucXL, double SoTien, Date NgayXL, int TrangThaiXL) {
        this.thanhvien = thanhvien;
        this.HinhThucXL = HinhThucXL;
        this.SoTien = SoTien;
        this.NgayXL = NgayXL;
        this.TrangThaiXL = TrangThaiXL;
    }
    
    public handle_violations(int MaXL, member thanhvien, String HinhThucXL, double SoTien, Date NgayXL, int TrangThaiXL) {
        this.MaXL = MaXL;
        this.thanhvien = thanhvien;
        this.HinhThucXL = HinhThucXL;
        this.SoTien = SoTien;
        this.NgayXL = NgayXL;
        this.TrangThaiXL = TrangThaiXL;
    }


    public handle_violations() {
    }

    public int getMaXL() {
        return MaXL;
    }

    public void setMaXL(int maXL) {
        MaXL = maXL;
    }

    public String getHinhThucXL() {
        return HinhThucXL;
    }

    public void setHinhThucXL(String hinhThucXL) {
        HinhThucXL = hinhThucXL;
    }

    public double getSoTien() {
        return SoTien;
    }

    public void setSoTien(double soTien) {
        SoTien = soTien;
    }

    public Date getNgayXL() {
        return NgayXL;
    }

    public void setNgayXL(Date ngayXL) {
        NgayXL = ngayXL;
    }

    public int getTrangThaiXL() {
        return TrangThaiXL;
    }

    public void setTrangThaiXL(int trangThaiXL) {
        TrangThaiXL = trangThaiXL;
    }

    public member getThanhvien() {
        return thanhvien;
    }

    public void setThanhvien(member thanhvien) {
        this.thanhvien = thanhvien;
    }
}
