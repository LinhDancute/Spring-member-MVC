package com.example.springmembermvc.Model.DTO.handle_violations;

import com.example.springmembermvc.Model.DTO.member.memberDTO;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class handle_violationsDTO {
    public int MaXL;
    public String HinhThucXL;
    public double SoTien;
    public Date NgayXL;
    public int TrangThaiXL;
    private memberDTO thanhvien;
}
