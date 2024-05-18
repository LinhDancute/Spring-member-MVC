package com.example.springmembermvc.Model.DTO.device;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class deviceDTO implements Serializable {
    private Integer MaTB;
    private String TenTB;
    private String MotaTB;
    private String HinhanhTB;
}
