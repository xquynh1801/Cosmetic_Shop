package com.example.lthdt.repository.model.dto;

import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.entity.NhanHieu;
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

    private String slug;

    private int tong_ban;

    private List<String> image;

    private String mota;

    private NhanHieu nhanHieu;

    private List<LoaiSPDTO> loaiSps;

    public SanPhamDTO(String id, String ten, String slug, int tong_ban, List<String> image, String mota, NhanHieu nhanHieu) {
        this.id = id;
        this.ten = ten;
        this.slug = slug;
        this.tong_ban = tong_ban;
        this.image = image;
        this.mota=mota;
        this.nhanHieu=nhanHieu;
        this.loaiSps = new ArrayList<>();
    }
}
