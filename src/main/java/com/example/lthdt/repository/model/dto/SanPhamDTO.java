package com.example.lthdt.repository.model.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SanPhamDTO {
    private String id;

    private String ten;

    private int tong_ban;

    private List<String> image;

    private String mota;

    private NhanHieuDTO nhanHieu;

    private List<LoaiSPDTO> loaiSps;

    public SanPhamDTO(String id, String ten, int tong_ban, List<String> image, String mota, NhanHieuDTO nhanHieu) {
        this.id = id;
        this.ten = ten;
        this.tong_ban = tong_ban;
        this.image = image;
        this.mota=mota;
        this.nhanHieu=nhanHieu;
        this.loaiSps = new ArrayList<>();
    }
}
