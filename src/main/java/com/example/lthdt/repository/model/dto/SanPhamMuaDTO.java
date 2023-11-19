package com.example.lthdt.repository.model.dto;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SanPhamMuaDTO {
    private long id;

    private SanPhamDTO sanPhamMua;

    private LoaiSPDTO loaiSanPhamMua;

    private int soluongmua;
}
