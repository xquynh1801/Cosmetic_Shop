package com.example.lthdt.service.impl;

import com.example.lthdt.entity.SanPham;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.repository.model.request.FilterSPReq;
import com.example.lthdt.service.SanPhamService;
import com.example.lthdt.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Override
    public List<SanPhamDTO> getListNewProduct() {
        List<SanPham> sanphams = sanPhamRepository.getAllAvailable(5);
        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:sanphams){
            sp.add(SanPhamMapper.toSanPhamDTO(s));
        }
        return sp;
    }

//    @Override
//    public List<SanPhamDTO> getListBestSellerProduct() {
//        List<SanPhamDTO> sanphams = sanPhamRepository.getListBestSellerProduct(5);
//        return sanphams;
//    }

    @Override
    public List<SanPhamDTO> getAllProduct() {
        List<SanPham> sanphams = sanPhamRepository.getAllPr();
        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:sanphams){
            sp.add(SanPhamMapper.toSanPhamDTO(s));
        }
        return sp;
    }

    @Override
    public TrangDTO filterProduct(FilterSPReq req) {
        int limit = 16;
        PageUtil page  = new PageUtil(limit, req.getPage());

        // Get list product and totalItems
        List<SanPham> products;
        products = sanPhamRepository.getAllPr();
        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:products){
            sp.add(SanPhamMapper.toSanPhamDTO(s));
        }
        for(SanPhamDTO s: sp) {
            List<LoaiSPDTO> loaiSp = loaiSPRepository.findLoaiSPtheoSanPhamID(s.getId());
            loaiSp.sort(new Comparator<LoaiSPDTO>() {
                @Override
                public int compare(LoaiSPDTO o1, LoaiSPDTO o2) {
                    return Long.compare(o1.getGia(), o2.getGia());
                }
            });
            s.setLoaiSps(loaiSp);
        }
        // Calculate total pages
        int totalPages = page.calculateTotalPage(sp.size());

        TrangDTO result = new TrangDTO(sp, totalPages, req.getPage());

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
        List<SanPham> products = sanPhamRepository.searchProductByKeyword(keyword, limit, pageInfo.calculateOffset());
        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:products){
            sp.add(SanPhamMapper.toSanPhamDTO(s));
        }
        for(SanPhamDTO s: sp) {
            List<LoaiSPDTO> loaiSp = loaiSPRepository.findLoaiSPtheoSanPhamID(s.getId());
            loaiSp.sort(new Comparator<LoaiSPDTO>() {
                @Override
                public int compare(LoaiSPDTO o1, LoaiSPDTO o2) {
                    return Long.compare(o1.getGia(), o2.getGia());
                }
            });
            s.setLoaiSps(loaiSp);
        }

        int totalItems = sanPhamRepository.countProductByKeyword(keyword);

        int totalPages = pageInfo.calculateTotalPage(totalItems);

        TrangDTO result = new TrangDTO(sp, totalPages, page);

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