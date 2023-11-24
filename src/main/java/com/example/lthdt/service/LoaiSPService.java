package com.example.lthdt.service;

import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.request.UpdateLoaiSP;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoaiSPService {
//    public List<LoaiSPDTO> findLoaiSPtheoSanPhamID(String id);
//
//    public List<LoaiSPDTO> findLoaiSPtheoSanPhamIDvaKhoangGia(String id, Long minPrice, Long maxPrice);

    public void updateLoaiSP(UpdateLoaiSP req);

    public void deleteLoaiSP(Long id);
}
