package com.example.lthdt.repository.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginReq {
    @NotNull(message = "Email trống")
    @NotEmpty(message = "Email trống")
    @Email(message = "Email không đúng định dạng")
    @ApiModelProperty(
            example="sam.smith@gmail.com",
            notes="Email trống",
            required=true
    )
    private String email;

    @ApiModelProperty(
            example="verysecretpassword",
            notes="Mật khẩu trống",
            required=true
    )
    private String matkhau;
}
