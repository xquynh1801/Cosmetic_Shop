package com.example.lthdt.service;

import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import com.example.lthdt.repository.model.request.CreateBrandReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NhanHieuService {
    public List<NhanHieuDTO> getListBrand();

    public NhanHieu createBrand(CreateBrandReq req);

    public void updateBrand(int id, CreateBrandReq req);

    public void deleteBrand(int id);

}
