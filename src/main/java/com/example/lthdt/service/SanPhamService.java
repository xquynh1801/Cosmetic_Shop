package com.example.lthdt.service;

import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.request.FilterSPReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SanPhamService {
    public List<SanPhamDTO> getListNewProduct();

    public List<SanPhamDTO> getListBestSellerProduct();

    public TrangDTO filterProduct(FilterSPReq req);

    public List<SanPhamDTO> getAllProduct();

    public TrangDTO searchProductByKeyword(String keyword, Integer page);

    public SanPhamDTO getDetailProductById(String id);
}
