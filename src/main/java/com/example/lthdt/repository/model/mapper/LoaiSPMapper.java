package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;

public class LoaiSPMapper {
    public static LoaiSPDTO toLoaiSPDTO(LoaiSanPham loaiSanPham){
        return new LoaiSPDTO(
                loaiSanPham.getId(),
                loaiSanPham.getTenloai(),
                loaiSanPham.getSoluong(),
                loaiSanPham.getGia());
    }
}
