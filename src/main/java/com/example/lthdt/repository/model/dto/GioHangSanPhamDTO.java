package com.example.lthdt.repository.model.dto;

import lombok.*;

import javax.persistence.Transient;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GioHangSanPhamDTO {
    private long id;

    private SanPhamDTO sanPham;

    private LoaiSPDTO loaiSP;

    private int soluong;
}
