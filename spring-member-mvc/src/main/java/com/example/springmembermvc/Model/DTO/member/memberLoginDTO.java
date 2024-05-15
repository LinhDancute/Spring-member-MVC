package com.example.springmembermvc.Model.DTO.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class memberLoginDTO implements Serializable {

    @NotEmpty(message = "MSSV không được để trống")
    @Pattern(regexp = "\\d{10}", message = "MSSV chỉ được chứa 10 ký tự")
    private int MaTV;

    @NotEmpty(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;
}
