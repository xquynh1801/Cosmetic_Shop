package com.example.lthdt.repository.model.dto;

import com.example.lthdt.entity.converter.StringListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;

    private String hoten;

    private String email;

    private String sdt;

    private String diachi;

    private String role;

}
