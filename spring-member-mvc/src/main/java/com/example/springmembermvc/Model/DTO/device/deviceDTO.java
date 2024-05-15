package com.example.springmembermvc.Model.DTO.device;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class deviceDTO implements Serializable {
    public Integer MaTB;
    public String TenTB;
    public String MotaTB;
    public String HinhanhTB;
    public int TrangThai;
}
