package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.GioHangSanPham;
import com.example.lthdt.repository.model.dto.GioHangSanPhamDTO;

public class GioHangSanPhamMapper {
    public static GioHangSanPhamDTO toGioHangSanPhamDTO(GioHangSanPham gioHangSanPham){
        return new GioHangSanPhamDTO(
                gioHangSanPham.getId(),
                SanPhamMapper.toSanPhamDTO(gioHangSanPham.getLoaiSanPham().getSanPhamLoai()),
                LoaiSPMapper.toLoaiSPDTO(gioHangSanPham.getLoaiSanPham()),
                gioHangSanPham.getSoluong());
    }
}
