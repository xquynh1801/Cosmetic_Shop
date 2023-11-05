package com.example.lthdt.service;

import com.example.lthdt.entity.GioHangSanPham;
import com.example.lthdt.repository.model.dto.TrangDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GioHangSanPhamService {
    public List<GioHangSanPham> findByCartId(Long id);

    public GioHangSanPham findByLoaiIdAndGioId(Long idLoai, Long idGio);
}
