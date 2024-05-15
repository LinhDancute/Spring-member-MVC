package com.example.springmembermvc.Model.DTO.usega_information;

import com.example.springmembermvc.Model.DTO.device.deviceDTO;
import com.example.springmembermvc.Model.DTO.member.memberDTO;

import java.util.Date;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class usage_informationDTO {
    public int MaTT;
    public LocalDateTime TGVao;
    public LocalDateTime TGMuon;
    public LocalDateTime TGTra;
    public Date TGDatcho;
    private deviceDTO thietbi;
    private memberDTO thanhvien;
}
