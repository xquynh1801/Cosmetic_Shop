package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.GioHangSanPham;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.model.dto.GioHangSanPhamDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;

public class SanPhamMapper {
    public static SanPhamDTO toSanPhamDTO(SanPham sanPham){
        return new SanPhamDTO(
                sanPham.getId(),
                sanPham.getTen(),
                sanPham.getTong_ban(),
                sanPham.getProductImages(),
                sanPham.getMota(),
                NhanHieuMapper.toNhanHieuDTO(sanPham.getNhanHieu()));
    }
}
