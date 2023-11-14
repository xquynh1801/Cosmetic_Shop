package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.GioHangSanPham;
import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.model.dto.GioHangSanPhamDTO;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;

import java.util.ArrayList;
import java.util.List;

public class SanPhamMapper {
    public static SanPhamDTO toSanPhamDTO(SanPham sanPham){
        List<LoaiSPDTO> loaiSPDTOS = new ArrayList<>();
        List<LoaiSanPham> loaiSPs = sanPham.getLoaiSanPhams();
        for(LoaiSanPham s:loaiSPs){
            loaiSPDTOS.add(LoaiSPMapper.toLoaiSPDTO(s));
        }
        return new SanPhamDTO(
                sanPham.getId(),
                sanPham.getTen(),
                sanPham.getTong_ban(),
                sanPham.getProductImages(),
                sanPham.getMota(),
                NhanHieuMapper.toNhanHieuDTO(sanPham.getNhanHieu()),
                loaiSPDTOS);
    }
}
