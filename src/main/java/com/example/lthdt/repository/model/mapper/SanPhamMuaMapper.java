package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.SanPhamMua;
import com.example.lthdt.repository.model.dto.DonHangDTO;
import com.example.lthdt.repository.model.dto.SanPhamMuaDTO;

public class SanPhamMuaMapper {
    public static SanPhamMuaDTO toSanPhamMuaDTO(SanPhamMua sanPhamMua){
        return new SanPhamMuaDTO(
                sanPhamMua.getId(),
                SanPhamMapper.toSanPhamDTO(sanPhamMua.getLoaiSanPhamMua().getSanPhamLoai()),
                LoaiSPMapper.toLoaiSPDTO(sanPhamMua.getLoaiSanPhamMua()),
                sanPhamMua.getSoluongmua());
    }
}
