package com.example.springmembermvc.Model.DTO.member;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class memberDTO implements Serializable {
    private int MaTV;
    private String HoTen;
    private String Khoa;
    private String Nganh;
    private String SDT;
    private String Password;
    private String Email;
}
