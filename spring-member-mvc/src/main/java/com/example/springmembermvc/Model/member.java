package com.example.springmembermvc.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Objects;
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
    private int MaTV;

    @Column(name = "HoTen")
    private String HoTen;

    @Column(name = "Khoa")
    private String Khoa;

    @Column(name = "Nganh")
    private String Nganh;

    @Column(name = "SDT", unique = true, nullable = true)
    private String SDT;

    @Column(name = "Password")
    private String Password;

    @Column(name = "Email", unique = true, nullable = true)
    private String Email;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        member member = (member) o;
        return MaTV == member.MaTV && Objects.equals(SDT, member.SDT) && Objects.equals(Email, member.Email) && Objects.equals(thongtinsd, member.thongtinsd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(MaTV, SDT, Email, thongtinsd);
    }
}
