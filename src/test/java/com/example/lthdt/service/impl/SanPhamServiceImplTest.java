package com.example.lthdt.service.impl;

import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.repository.model.request.FilterSPReq;
import com.example.lthdt.service.SanPhamService;
import com.example.lthdt.util.PageUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class SanPhamServiceImplTest {

    @InjectMocks
    SanPhamServiceImpl sanPhamService;

    @Mock
    SanPhamRepository sanPhamRepository;

    @Mock
    LoaiSPRepository loaiSPRepository;

    @Test
    void getListNewProduct() {
        List<SanPham> sp = new ArrayList<>();
        for(int i=0; i<5; i++){
            sp.add(new SanPham(i+""));
        }
        List<SanPhamDTO> spDTO = new ArrayList<>();
        for(SanPham s:sp){
            spDTO.add(SanPhamMapper.toSanPhamDTO(s));
        }
        when(sanPhamRepository.getAllAvailable(5)).thenReturn(sp);

        List<SanPhamDTO> expected_result = sanPhamService.getListNewProduct();
        assertEquals(expected_result.size(), spDTO.size());
    }

    @Test
    void getAllProduct() {
        List<SanPham> sp = new ArrayList<>();
        for(int i=0; i<5; i++){
            sp.add(new SanPham(i+""));
        }
        List<SanPhamDTO> spDTO = new ArrayList<>();
        for(SanPham s:sp){
            spDTO.add(SanPhamMapper.toSanPhamDTO(s));
        }
        when(sanPhamRepository.getAllPr()).thenReturn(sp);

        List<SanPhamDTO> expected_result = sanPhamService.getAllProduct();
        assertEquals(expected_result.size(), spDTO.size());
    }

    @Test
    void filterProduct() {
        List<Integer> brands = new ArrayList<>();
        brands.add(0);
        FilterSPReq req = new FilterSPReq(brands);

        List<SanPham> products = new ArrayList<>();
        products.add(new SanPham(0+""));

        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:products){
            SanPhamDTO tmp_sp = SanPhamMapper.toSanPhamDTO(s);
        }

        List<LoaiSPDTO> loaiSp = new ArrayList<>();

        PageUtil page  = new PageUtil(16, req.getPage());
        int totalPages = page.calculateTotalPage(sp.size());
        TrangDTO result = new TrangDTO(sp, totalPages, req.getPage());

        when(sanPhamRepository.locSanPham(req.getBrands())).thenReturn(products);
        when(loaiSPRepository.findLoaiSPtheoSanPhamIDvaKhoangGia(0+"", req.getMinPrice(), req.getMaxPrice())).thenReturn(loaiSp);

        TrangDTO expected_result = sanPhamService.filterProduct(req);
        assertTrue(expected_result.getItems().equals(result.getItems()));
    }

    @Test
    void searchProductByKeyword() {
        String keyword = "nuoc hoa";
        Integer page = 1;

        int limit = 15;
        PageUtil pageInfo = new PageUtil(limit, page);

        List<SanPham> products = new ArrayList<>();
        when(sanPhamRepository.searchProductByKeyword(keyword, limit, pageInfo.calculateOffset())).thenReturn(products);

        List<SanPhamDTO> sp = new ArrayList<>();
        for(SanPham s:products){
            SanPhamDTO tmp_sp = SanPhamMapper.toSanPhamDTO(s);
            List<LoaiSPDTO> loaiSp = new ArrayList<>();
            when(loaiSPRepository.findLoaiSPtheoSanPhamID(tmp_sp.getId())).thenReturn(loaiSp);
            loaiSp.sort(new Comparator<LoaiSPDTO>() {
                @Override
                public int compare(LoaiSPDTO o1, LoaiSPDTO o2) {
                    return Long.compare(o1.getGia(), o2.getGia());
                }
            });
            tmp_sp.setLoaiSps(loaiSp);
            sp.add(tmp_sp);
        }

        int totalItems = 0;
        when(sanPhamRepository.countProductByKeyword(keyword)).thenReturn(totalItems);

        int totalPages = pageInfo.calculateTotalPage(totalItems);

        TrangDTO result = new TrangDTO(sp, totalPages, page);
        TrangDTO exp_result = sanPhamService.searchProductByKeyword(keyword, page);
        assertTrue(exp_result.getItems().equals(result.getItems()));
    }

    @Test
    void getDetailProductById() {
    }
}