package com.example.lthdt.controller.admin;

import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.entity.User;
import com.example.lthdt.repository.NhanHieuRepository;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import com.example.lthdt.repository.model.request.CreateBrandReq;
import com.example.lthdt.service.ImageService;
import com.example.lthdt.service.NhanHieuService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class QLNhanHieuController {

    @Autowired
    private NhanHieuService nhanHieuService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/admin/brands")
    public String getPostManagePage(Model model) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);

        List<NhanHieuDTO> brands = nhanHieuService.getListBrand();
        model.addAttribute("brands", brands);

        return "admin/nhanhieu/list";
    }

    @PostMapping("/api/admin/brands")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateBrandReq req) {
        NhanHieu brand = nhanHieuService.createBrand(req);

        return ResponseEntity.ok(brand);
    }

    @PutMapping("/api/admin/brands/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CreateBrandReq req, @PathVariable int id) {
        nhanHieuService.updateBrand(id, req);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/brands/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        nhanHieuService.deleteBrand(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
