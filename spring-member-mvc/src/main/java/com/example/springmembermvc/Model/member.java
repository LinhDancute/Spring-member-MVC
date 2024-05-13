package com.example.springmembermvc.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Entity
@ToString
@Table(name = "thanhvien")
@Accessors(chain = true)
public class member {
    
    @Id
    @Column(name = "MaTV")
    public int MaTV;
    
    @Column(name = "HoTen")
    public String HoTen;
    
    @Column(name = "Khoa")
    public String Khoa;
    
    @Column(name = "Nganh")
    public String Nganh;
    
    @Column(name = "SDT", unique = true, nullable = true)
    public String SDT;
    
    @Column(name = "Password")
    public String Password;
    
    @Column(name = "Email", unique = true, nullable = true)
    public String Email;

    @OneToMany(mappedBy = "thanhvien")
    private Set<usage_information> thongtinsd;
    
    public member(int MaTV, String HoTen, String Khoa, String Nganh, String SDT, String Password, String Email) {
        this.MaTV = MaTV;
        this.HoTen = HoTen;
        this.Khoa = Khoa;
        this.Nganh = Nganh;
        this.SDT = SDT;
        this.Password = Password;
        this.Email = Email;
    }
    
     public member(int MaTV) {
        this.MaTV = MaTV;
    }

    public member() {
    }

    public int getMaTV() {
        return MaTV;
    }

    public void setMaTV(int maTV) {
        MaTV = maTV;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getKhoa() {
        return Khoa;
    }

    public void setKhoa(String khoa) {
        Khoa = khoa;
    }

    public String getNganh() {
        return Nganh;
    }

    public void setNganh(String nganh) {
        Nganh = nganh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Set<usage_information> getThongtinsd() {
        return thongtinsd;
    }

    public void setThongtinsd(Set<usage_information> thongtinsd) {
        this.thongtinsd = thongtinsd;
    }

    // Constructor mặc định không có tham số
}

    
