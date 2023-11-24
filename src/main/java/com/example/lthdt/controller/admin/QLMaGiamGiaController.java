package com.example.lthdt.controller.admin;

import com.example.lthdt.entity.MaGiamGia;
import com.example.lthdt.repository.MaGiamGiaRepository;
import com.example.lthdt.repository.model.request.CreatePromotionReq;
import com.example.lthdt.service.MaGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class QLMaGiamGiaController {
    @Autowired
    private MaGiamGiaRepository maGiamGiaRepository;

    @Autowired
    private MaGiamGiaService maGiamGiaService;

    @GetMapping("/admin/magiamgia")
    public String getPromotionManagePage(Model model) {
        List<MaGiamGia> maGiamGiaList = maGiamGiaRepository.findAll();
        model.addAttribute("maGiamGiaList", maGiamGiaList);

        return "admin/magiamgia/list";
    }

    @GetMapping("/admin/magiamgia/create")
    public String getPromotionCreatePage(Model model) {
        return "admin/magiamgia/create";
    }

    @PostMapping("/api/admin/magiamgia")
    public ResponseEntity<?> createPromotion(@Valid @RequestBody CreatePromotionReq req) {
        MaGiamGia maGiamGia = maGiamGiaService.createPromotion(req);

        return ResponseEntity.ok(maGiamGia.getId());
    }

    @GetMapping("/admin/magiamgia/{id}")
    public String getPromotionDetailPage(Model model, @PathVariable long id) {
        Optional<MaGiamGia> rs = maGiamGiaRepository.findById(id);
        MaGiamGia maGiamGia = rs.get();

        model.addAttribute("maGiamGia", maGiamGia);

        return "admin/magiamgia/detail";
    }

    @PutMapping("/api/admin/magiamgia/{id}")
    public ResponseEntity<?> updatePromotion(@Valid @RequestBody CreatePromotionReq req, @PathVariable long id) {
        maGiamGiaService.updatePromotion(id, req);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/magiamgia/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable long id) {
        maGiamGiaService.deletePromotion(id);

        return ResponseEntity.ok("Xóa khuyến mãi thành công");
    }
}
