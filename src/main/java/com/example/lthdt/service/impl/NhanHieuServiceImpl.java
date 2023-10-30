package com.example.lthdt.service.impl;

import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.repository.NhanHieuRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import com.example.lthdt.service.NhanHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NhanHieuServiceImpl implements NhanHieuService {
    @Autowired
    private NhanHieuRepository nhanHieuRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<NhanHieu> getListBrand() {
        return nhanHieuRepository.findAll();
    }

    @Override
    public List<NhanHieuDTO> getListBrandAndProductCount() {
        return nhanHieuRepository.getListBrandAndProductCount();
    }
}
