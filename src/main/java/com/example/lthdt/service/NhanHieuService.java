package com.example.lthdt.service;

import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NhanHieuService {
    public List<NhanHieu> getListBrand();

    public List<NhanHieuDTO> getListBrandAndProductCount();
}
