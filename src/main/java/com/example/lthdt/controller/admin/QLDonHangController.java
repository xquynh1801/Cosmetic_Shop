package com.example.lthdt.controller.admin;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.MaGiamGia;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.DonHangRepository;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.MaGiamGiaRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.DonHangDTO;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.mapper.DonHangMapper;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.service.DonHangService;
import com.example.lthdt.service.SanPhamService;
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
public class QLDonHangController {
    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Autowired
    private MaGiamGiaRepository maGiamGiaRepository;

    @GetMapping("/admin/donhang")
    public String getOrderManagePage(Model model) {
        // Get list product to select
        List<DonHang> donHangList = donHangRepository.findAll();
        List<DonHangDTO> donHangDTOList = new ArrayList<>();
        for(DonHang dh:donHangList){
            donHangDTOList.add(DonHangMapper.toDonHangDTO(dh));
        }

        model.addAttribute("donHangDTOList", donHangDTOList);

        return "admin/donhang/list";
    }

    @GetMapping("/admin/donhang/create")
    public String getOrderCreatePage(Model model) {
        // Get list product to select
        List<SanPhamDTO> products = sanPhamService.getAllAvailable();
        model.addAttribute("products", products);

        List<MaGiamGia> maGiamGiaList = maGiamGiaRepository.findAll();
        model.addAttribute("maGiamGiaList", maGiamGiaList);

        return "admin/donhang/create";
    }

    @GetMapping("/api/getLoaiSps/productId={productId}")
    public ResponseEntity<?> getLoaiSps(@PathVariable String productId) {
            Optional<SanPham> sanPham = sanPhamRepository.findById(productId);
            SanPhamDTO sanPhamDTO = SanPhamMapper.toSanPhamDTO(sanPham.get());

            List<LoaiSPDTO> loaiSps = sanPhamDTO.getLoaiSps();
            return ResponseEntity.ok(loaiSps);
    }

    @GetMapping("/admin/donhang/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        Optional<DonHang> rs = donHangRepository.findById(id);
        DonHang donHang = rs.get();

        DonHangDTO order = DonHangMapper.toDonHangDTO(donHang);
        model.addAttribute("order", order);

        return "admin/donhang/detail";
    }

    @PutMapping("/api/admin/donhang/{id}/update-status")
    public ResponseEntity<?> updateStatusOrder(@PathVariable long id) {
        donHangRepository.update(1, id);

        return ResponseEntity.ok("Xác nhận thành công");
    }
}
