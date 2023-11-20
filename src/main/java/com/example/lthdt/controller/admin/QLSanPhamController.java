package com.example.lthdt.controller.admin;

import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.request.FilterSPReq;
import com.example.lthdt.service.LoaiSPService;
import com.example.lthdt.service.NhanHieuService;
import com.example.lthdt.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class QLSanPhamController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Autowired
    private LoaiSPService loaiSPService;

    @Autowired
    private NhanHieuService nhanHieuService;

    @GetMapping("/admin/products")
    public String getListProduct(Model model) {
        // Get list brand
        List<NhanHieuDTO> brands = nhanHieuService.getListBrand();
        model.addAttribute("brands", brands);


        ArrayList<Integer> brandIds = new ArrayList<Integer>();
        for (NhanHieuDTO brand : brands) {
            brandIds.add(brand.getId());
        }
        model.addAttribute("brandIds", brandIds);

        // Get list product
        FilterSPReq req = new FilterSPReq(brandIds, (long) 0, Long.MAX_VALUE, 1);
        TrangDTO result = sanPhamService.filterProduct(req);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("products", result.getItems());

        return "admin/sanpham/list";
    }

    @GetMapping("/admin/products/{id}")
    public String getDetailProductPage(Model model, @PathVariable String id) {
        try {
            // Get info
            Optional<SanPham> result = sanPhamRepository.findById(id);
            SanPham product = result.get();
            model.addAttribute("product", product);

            // Get list image of user
//            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
//            List<String> images = imageService.getListImageOfUser(user.getId());
//            model.addAttribute("images", images);

            // Get list loaiSP
            List<LoaiSanPham> loaiSanPhamList = loaiSPRepository.findAll();
            model.addAttribute("loaiSanPhamList", loaiSanPhamList);

            // Get list brand
            List<NhanHieuDTO> brands = nhanHieuService.getListBrand();
            model.addAttribute("brands", brands);

            // Get list loai cua sp
            List<LoaiSPDTO> loaiSPListOfSP = loaiSPRepository.findLoaiSPtheoSanPhamID(product.getId());
            model.addAttribute("loaiSPListOfSP", loaiSPListOfSP);

            return "admin/sanpham/detail";
        } catch (NotFoundException ex) {
            return "error/admin";
        }
    }
}
