package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;

public class NhanHieuMapper {
    public static NhanHieuDTO toNhanHieuDTO(NhanHieu nhanHieu){
        return new NhanHieuDTO(
                nhanHieu.getId(),
                nhanHieu.getName(),
                nhanHieu.getMota(),
                nhanHieu.getImage(),
                nhanHieu.getSanPhams().size());
    }
}
