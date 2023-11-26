package com.example.lthdt.controller.admin;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.User;
import com.example.lthdt.repository.*;
import com.example.lthdt.repository.model.dto.DonHangDTO;
import com.example.lthdt.repository.model.dto.UserDTO;
import com.example.lthdt.repository.model.mapper.DonHangMapper;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.repository.model.request.ThongKeReq;
import com.example.lthdt.repository.model.request.ThongKeResponse;
import com.example.lthdt.service.DonHangService;
import com.example.lthdt.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ThongKeController {
    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private DonHangService donHangService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Autowired
    private MaGiamGiaRepository maGiamGiaRepository;

    @GetMapping("/admin/thongke")
    public String getTK(Model model) {
        return "admin/thongke/tkdt";
    }

    @PostMapping("/api/admin/thongke")
    public ResponseEntity<?> getTK(@Valid @RequestBody ThongKeReq req) {
        Date batdau = req.getBatDauDate();
        Date ketthuc = req.getKetThucDate();
        Set<UserDTO> uniqueUsers = new HashSet<>();
        List<DonHang> donHangList = donHangRepository.findOrdersByModifiedAtBetween(batdau, ketthuc);
        List<DonHangDTO> donHangDTOList = new ArrayList<>();
        if(donHangList != null){
            for (DonHang dh : donHangList){
                donHangDTOList.add(DonHangMapper.toDonHangDTO(dh));
            }

            long tongdoanhthu = 0;
            for (DonHangDTO donHang : donHangDTOList) {
                tongdoanhthu += donHang.getTongtra();
                UserDTO nguoiDatDTO = donHang.getNguoidat();
                uniqueUsers.add(nguoiDatDTO);
            }

            List<UserDTO> userList = new ArrayList<>(uniqueUsers);
            ThongKeResponse response = new ThongKeResponse(userList, tongdoanhthu, batdau, ketthuc);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.ok("Khong tim thay");
    }

    @GetMapping("/orderListOfUser")
    public String getOrderList(Model model,
                               @RequestParam(name = "id", required = true) Long id,
                               @RequestParam(name = "batdau", required = false) Long batdauTimestamp,
                               @RequestParam(name = "ketthuc", required = false) Long ketthucTimestamp) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Chuyển đổi timestamp thành đối tượng Date
        Date batdau = (batdauTimestamp != null) ? new Date(batdauTimestamp) : null;
        Date ketthuc = (ketthucTimestamp != null) ? new Date(ketthucTimestamp) : null;

        Optional<User> rs = userRepository.findById(id);
        UserDTO user = UserMapper.toUserDto(rs.get());
        List<DonHang> donHangList = donHangRepository.findOrdersOfUser(batdau, ketthuc, rs.get());

        List<DonHangDTO> donHangDTOList = new ArrayList<>();
        for (DonHang dh : donHangList){
            donHangDTOList.add(DonHangMapper.toDonHangDTO(dh));
        }
        long tongtien = 0;
        for (DonHangDTO donHang : donHangDTOList) {
            tongtien += donHang.getTongtra();
        }

        model.addAttribute("user", user);
        model.addAttribute("donHangDTOList", donHangDTOList);
        model.addAttribute("tongtien", tongtien);
        model.addAttribute("batdau", batdau);
        model.addAttribute("ketthuc", ketthuc);

        return "admin/thongke/orderList";
    }
}
