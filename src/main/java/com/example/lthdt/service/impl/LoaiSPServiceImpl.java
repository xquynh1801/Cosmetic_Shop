package com.example.lthdt.service.impl;

import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.mapper.LoaiSPMapper;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.service.LoaiSPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoaiSPServiceImpl implements LoaiSPService {
    @Autowired
    private LoaiSPRepository loaiSPRepository;

//    @Override
//    public List<LoaiSPDTO> findLoaiSPtheoSanPhamID(String id) {
//        List<LoaiSanPham> loais = loaiSPRepository.findLoaiSPtheoSanPhamID(id);
//        List<LoaiSPDTO> loaisps = new ArrayList<>();
//        for(LoaiSanPham s:loais){
//            loaisps.add(LoaiSPMapper.toLoaiSPDTO(s));
//        }
//        return loaisps;
//    }
//
//    @Override
//    public List<LoaiSPDTO> findLoaiSPtheoSanPhamIDvaKhoangGia(String id, Long minPrice, Long maxPrice) {
//        List<LoaiSanPham> loais = loaiSPRepository.findLoaiSPtheoSanPhamIDvaKhoangGia(id, minPrice, maxPrice);
//        List<LoaiSPDTO> loaisps = new ArrayList<>();
//        for(LoaiSanPham s:loais){
//            loaisps.add(LoaiSPMapper.toLoaiSPDTO(s));
//        }
//        return loaisps;
//    }
}
