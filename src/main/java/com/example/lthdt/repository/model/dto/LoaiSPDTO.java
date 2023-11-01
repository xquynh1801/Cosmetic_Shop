package com.example.lthdt.repository.model.dto;

import lombok.*;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoaiSPDTO {
    private long id;

    private String tenloai;

    private int soluong;

    private long gia;
}
