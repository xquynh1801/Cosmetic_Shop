package com.example.lthdt.repository.model.request;

import com.example.lthdt.entity.GioHang;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateUserReq {
    @NotNull(message = "Họ tên trống")
    @NotEmpty(message = "Họ tên trống")
    @ApiModelProperty(
            example="Sam Smith",
            notes="Họ tên trống",
            required=true
    )
    @JsonProperty("hoten")
    private String hoten;

    @NotNull(message = "Email trống")
    @NotEmpty(message = "Email trống")
    @Email(message = "Email không đúng định dạng")
    @ApiModelProperty(
            example="sam.smith@gmail.com",
            notes="Email trống",
            required=true
    )
    private String email;

//    @NotNull(message = "Mật khẩu trống")
//    @NotEmpty(message = "Mật khẩu trống")
//    @Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4 - 20 ký tự")
    @ApiModelProperty(
            example="verysecretpassword",
            notes="Mật khẩu trống",
            required=true
    )
    private String matkhau;

    private String sdt;

}
