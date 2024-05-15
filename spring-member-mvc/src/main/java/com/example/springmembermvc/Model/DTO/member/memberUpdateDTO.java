package com.example.springmembermvc.Model.DTO.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class memberUpdateDTO implements Serializable {
    private Integer id;

    @NotBlank(message = "Tên người dùng không được để trống")
    private String name;

    @NotBlank(message = "Khoa không được để trống")
    private String department;

    @Email(message = "Email sai định dạng")
    private String email;

    @NotBlank(message = "Ngành không được để trống")
    private String major;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại sai định dạng")
    private String phoneNumber;
}
