package com.example.lthdt.service.impl;

import com.example.lthdt.entity.SanPham;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.repository.model.request.FilterSPReq;
import com.example.lthdt.service.SanPhamService;
import com.example.lthdt.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPhamDTO> getListNewProduct() {
        List<SanPham> sanphams = sanPhamRepository.getAllAvailable(5);
        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:sanphams){
            sp.add(SanPhamMapper.toSanPhamDTO(s));
        }
        return sp;
    }

    @Override
    public List<SanPhamDTO> getListBestSellerProduct() {
        List<SanPhamDTO> sanphams = sanPhamRepository.getListBestSellerProduct(5);
        return sanphams;
    }

    @Override
    public List<SanPhamDTO> getAllProduct() {
        List<SanPhamDTO> sanphams = sanPhamRepository.getAllProduct();
        return sanphams;
    }

    @Override
    public TrangDTO filterProduct(FilterSPReq req) {
        int limit = 16;
        PageUtil page  = new PageUtil(limit, req.getPage());

        // Get list product and totalItems
        List<SanPhamDTO> products;
        products = sanPhamRepository.getAllProduct();

        // Calculate total pages
        int totalPages = page.calculateTotalPage(products.size());

        TrangDTO result = new TrangDTO(products, totalPages, req.getPage());

        return result;
    }

    @Override
    public TrangDTO searchProductByKeyword(String keyword, Integer page) {
        // Validate
        if (keyword == null) {
            keyword = "";
        }
        if (page == null) {
            page = 1;
        }

        int limit = 15;
        PageUtil pageInfo = new PageUtil(limit, page);

        // Get list product and totalItems
        List<SanPhamDTO> products = sanPhamRepository.searchProductByKeyword(keyword, limit, pageInfo.calculateOffset());

        int totalItems = sanPhamRepository.countProductByKeyword(keyword);

        int totalPages = pageInfo.calculateTotalPage(totalItems);

        TrangDTO result = new TrangDTO(products, totalPages, page);

        return result;
    }

    @Override
    public SanPhamDTO getDetailProductById(String id) {
        // Get product info
        Optional<SanPham> result = sanPhamRepository.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        SanPham product = result.get();

        if (!product.isAvailable()) {
            throw new NotFoundException("Sản phẩm không tồn tại");
        }

        SanPhamDTO dto = SanPhamMapper.toSanPhamDTO(product);

        return dto;
    }
}