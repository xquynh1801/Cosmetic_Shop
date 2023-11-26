package com.example.lthdt.controller.nvgh;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.HoaDon;
import com.example.lthdt.entity.User;
import com.example.lthdt.repository.DonHangRepository;
import com.example.lthdt.repository.HoaDonRepository;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.DonHangDTO;
import com.example.lthdt.repository.model.mapper.DonHangMapper;
import com.example.lthdt.service.DonHangService;
import com.example.lthdt.service.SanPhamService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class GHDonHangController {
    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private DonHangService donHangService;

    @GetMapping("/nvgh/donhang")
    public String getOrderManagePage(Model model,
                                     @RequestParam(defaultValue = "%%") String trangthai) {
        // Get list product to select
        List<DonHang> donHangList = donHangRepository.findAll();
        List<DonHangDTO> donHangDTOList = new ArrayList<>();
        for(DonHang dh:donHangList){
            donHangDTOList.add(DonHangMapper.toDonHangDTO(dh));
        }

        List<DonHang> donHangList1 = donHangService.adminGetListDH(trangthai);
        model.addAttribute("donHangDTOList", donHangList1);

        return "nvgh/list";
    }

    @GetMapping("/nvgh/donhang/{id}")
    public String getOrderDetailPage(Model model, @PathVariable long id) {
        Optional<DonHang> rs = donHangRepository.findById(id);
        DonHang donHang = rs.get();

        DonHangDTO order = DonHangMapper.toDonHangDTO(donHang);
        model.addAttribute("order", order);

        return "nvgh/detail";
    }

    @PutMapping("/api/nvgh/donhang/{id}/update-status")
    public ResponseEntity<?> updateStatusOrder(@PathVariable long id) {
        Timestamp modifiedAt = new Timestamp(System.currentTimeMillis());
        User modifiedBy = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Optional<DonHang> rs = donHangRepository.findById(id);
        DonHang donHang = rs.get();


        if(donHang.getTrangthai() == 1){
            donHangRepository.update(2, modifiedAt, modifiedBy,id);
        }else if (donHang.getTrangthai() == 2){
            donHangRepository.update(3, modifiedAt, modifiedBy,id);
            if(donHang.getIsPaid() == 0){
                HoaDon hoaDon = new HoaDon("Thanh toán khi nhận hàng", donHang, donHang.getTongtra(), "");
                hoaDonRepository.save(hoaDon);
            }
        }

        return ResponseEntity.ok("Xác nhận thành công");
    }

    @PutMapping("/api/nvgh/donhang/{id}/update-cancel")
    public ResponseEntity<?> updateCancelOrder(@PathVariable long id) {
        Timestamp modifiedAt = new Timestamp(System.currentTimeMillis());
        User modifiedBy = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        donHangRepository.update(4, modifiedAt, modifiedBy,id);

        return ResponseEntity.ok("Xác nhận thành công");
    }
}
