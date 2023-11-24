package com.example.lthdt.controller.admin;

import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.entity.User;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.request.CreateProductReq;
import com.example.lthdt.repository.model.request.FilterSPReq;
import com.example.lthdt.repository.model.request.UpdateLoaiSP;
import com.example.lthdt.service.ImageService;
import com.example.lthdt.service.LoaiSPService;
import com.example.lthdt.service.NhanHieuService;
import com.example.lthdt.service.SanPhamService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @Autowired
    private ImageService imageService;

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
            User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            List<String> images = imageService.getListImageOfUser(user.getId());
            model.addAttribute("images", images);

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

    @PutMapping("/api/admin/products/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody CreateProductReq req) {
        sanPhamService.updateProduct(id, req);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @PutMapping("/api/admin/products/updatelsp")
    public ResponseEntity<?> updateLoaiSanPham(@Valid @RequestBody UpdateLoaiSP req) {
        if(req.getId() != 0){
            LoaiSanPham loaiSanPham = loaiSPRepository.findByTenloaiAndAndSanPhamLoai(req.getTenloai(), req.getProductId());

            if(loaiSanPham != null && loaiSanPham.getId() == req.getId()){
                loaiSPService.updateLoaiSP(req);
                return ResponseEntity.ok("Cap nhat thanh cong");
            }else if(loaiSanPham != null && loaiSanPham.getId() != req.getId()){
                return ResponseEntity.ok("Loai san pham trung ten");
            }
        }

        return ResponseEntity.ok("Chua ton tai san pham");
    }

    @PutMapping("/api/admin/products/insertlsp")
    public ResponseEntity<?> insertLoaiSanPham(@Valid @RequestBody UpdateLoaiSP req) {
        if (req.getId() == 0){
            LoaiSanPham loaiSanPham = loaiSPRepository.findByTenloaiAndAndSanPhamLoai(req.getTenloai(), req.getProductId());
            Optional<SanPham> rs = sanPhamRepository.findById(req.getProductId());

            if(loaiSanPham == null){
                LoaiSanPham lsp = new LoaiSanPham();
                lsp.setTenloai(req.getTenloai());
                lsp.setSoluong(req.getSoluong());
                lsp.setGia(req.getGia());
                lsp.setSanPhamLoai(rs.get());
                loaiSPRepository.save(lsp);

                return ResponseEntity.ok("Them thanh cong");
            }else if (loaiSanPham != null){
                return ResponseEntity.ok("Loai san pham trung ten");
            }
        }

        return ResponseEntity.ok("Da ton tai loai san pham");
    }


    @DeleteMapping("/api/admin/products/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        sanPhamService.deleteProduct(id);

        return ResponseEntity.ok("Xóa thành công");
    }

    @GetMapping("/api/delete/loaisanpham/{id}")
    public String deleteloaisanpham(@PathVariable Long id) {
        loaiSPService.deleteLoaiSP(id);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/create")
    public String getProductCreatePage(Model model) {
        // Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);

        // Get list brand
        List<NhanHieuDTO> brands = nhanHieuService.getListBrand();
        model.addAttribute("brands", brands);

        return "admin/sanpham/create";
    }

    @PostMapping("/api/admin/products")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductReq req) {
        String productId = sanPhamService.createProduct(req);
        return ResponseEntity.ok(productId);
    }
}
