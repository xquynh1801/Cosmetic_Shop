package com.example.lthdt.controller;

import com.example.lthdt.entity.*;
import com.example.lthdt.repository.GioHangRepository;
import com.example.lthdt.repository.GioHangSanPhamRepository;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.model.dto.*;
import com.example.lthdt.repository.model.mapper.GioHangSanPhamMapper;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.repository.model.request.AddToCartRequest;
import com.example.lthdt.service.GioHangSanPhamService;
import com.example.lthdt.service.GioHangService;
import com.example.lthdt.service.SanPhamService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import com.example.lthdt.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Controller
public class GioHangController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GioHangSanPhamRepository gioHangSanPhamRepository;

    @Autowired
    private GioHangSanPhamService gioHangSanPhamService;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Autowired
    private GioHangService gioHangService;

    @PostMapping("/them-vao-gio-hang")
    public ResponseEntity<?> themVaoGioHang(@Valid @RequestBody AddToCartRequest req, HttpServletResponse response) {
        // Lấy người dùng hiện đang đăng nhập (sử dụng Spring Security)
        User currentUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserDTO user = UserMapper.toUserDto((currentUser));

        // Tạo một đối tượng GioHangSanPham và lưu nó vào cơ sở dữ liệu
        Optional<LoaiSanPham> result = loaiSPRepository.findById(req.getLoaiSPId());
        LoaiSanPham loaiSanPham = result.get();

        GioHang gioHang = gioHangService.findByUserId(user.getId());

        GioHangSanPham cart_product = gioHangSanPhamRepository.findByLoaiIdAndGioId(req.getLoaiSPId(), gioHang.getId());

        if(cart_product != null){
            int soluong = cart_product.getSoluong() + req.getSoLuong();
            System.out.println("=====================>cart_productSL: " + cart_product.getSoluong());
            System.out.println("=====================>SL: " + soluong);
            gioHangSanPhamRepository.update(soluong, cart_product.getId());
        }else{
            GioHangSanPham gioHangSanPham = new GioHangSanPham();
            gioHangSanPham.setLoaiSanPham(loaiSanPham);
            gioHangSanPham.setSoluong(req.getSoLuong());
            gioHangSanPham.setDaMua(false);

            // Thêm sản phẩm vào giỏ hàng của người dùng hiện tại
            gioHangSanPham.setGioHang(gioHang);
            gioHangSanPhamRepository.save(gioHangSanPham);
        }

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/gio-hang")
    public String showGioHang(Model model){
        int limit = 16;
        PageUtil pages  = new PageUtil(limit, 1);
        // Lấy người dùng hiện đang đăng nhập (sử dụng Spring Security)
        User currentUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserDTO user = UserMapper.toUserDto((currentUser));

        GioHang gioHang = gioHangService.findByUserId(user.getId());
        List<GioHangSanPham> cart_sps = gioHangSanPhamService.findByCartId(gioHang.getId());

        List<GioHangSanPhamDTO> rs = new ArrayList<>();
        for(GioHangSanPham s:cart_sps){
            GioHangSanPhamDTO tmp_cart_sps = GioHangSanPhamMapper.toGioHangSanPhamDTO(s);
            rs.add(tmp_cart_sps);
        }

        // Calculate total pages
        int totalPages = pages.calculateTotalPage(rs.size());
        TrangDTO result = new TrangDTO(rs, totalPages, 1);

        // tensp, nhanhieu
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("listCart_sp", result.getItems());

        return "shop/cart";
    }
}
