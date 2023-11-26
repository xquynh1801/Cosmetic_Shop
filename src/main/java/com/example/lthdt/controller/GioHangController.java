package com.example.lthdt.controller;

import com.example.lthdt.entity.*;
import com.example.lthdt.repository.GioHangRepository;
import com.example.lthdt.repository.GioHangSanPhamRepository;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.model.dto.*;
import com.example.lthdt.repository.model.mapper.GioHangSanPhamMapper;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.repository.model.request.AddToCartRequest;
import com.example.lthdt.repository.model.request.DeleteAllProductCartRequest;
import com.example.lthdt.repository.model.request.UpdateGioHangSPReq;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
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
        model.addAttribute("cart_id", gioHang.getId());
        List<GioHangSanPham> cart_sps = gioHangSanPhamService.findByCartId(gioHang.getId());

        long tong_tien = 0;

        List<GioHangSanPhamDTO> rs = new ArrayList<>();
        for(GioHangSanPham s:cart_sps){
            GioHangSanPhamDTO tmp_cart_sps = GioHangSanPhamMapper.toGioHangSanPhamDTO(s);
            rs.add(tmp_cart_sps);
            tong_tien += (s.getLoaiSanPham().getGia()*s.getSoluong());
        }

        // Calculate total pages
        int totalPages = pages.calculateTotalPage(rs.size());
        TrangDTO result = new TrangDTO(rs, totalPages, 1);

        // tensp, nhanhieu
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("currentPage", result.getCurrentPage());
        model.addAttribute("listCart_sp", result.getItems());
        model.addAttribute("tong_tien", tong_tien);

        return "shop/cart";
    }

    @PostMapping("/xoa-allproductcart")
    public ResponseEntity<?> xoaAllProductCart(@Valid @RequestBody DeleteAllProductCartRequest req, HttpServletResponse response){
        Optional<GioHang> result = gioHangRepository.findById(req.getCart_id());
        GioHang gioHang = result.get();
        gioHangSanPhamRepository.delete(gioHang.getId());

        return ResponseEntity.ok("ok");
    }

    @RequestMapping("/delete/cart_product/{id}")
    public String deleteCartProduct(@PathVariable long id){
        gioHangSanPhamRepository.deleteById(id);

        return "redirect:/gio-hang";
    }

    @GetMapping("/fix_cart/{id}")
    public String FixCart(@PathVariable long id, Model model){
        Optional<GioHangSanPham> result = gioHangSanPhamRepository.findById(id);
        GioHangSanPhamDTO gioHangSanPham= GioHangSanPhamMapper.toGioHangSanPhamDTO(result.get());
        model.addAttribute("gioHangSanPham", gioHangSanPham);
        return "shop/fix-cart";
    }

    @PostMapping("/api/update-cartproduct")
    public ResponseEntity<?> UpdateGioHangSP(@Valid @RequestBody UpdateGioHangSPReq req) {
        // Lấy người dùng hiện đang đăng nhập (sử dụng Spring Security)
        User currentUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserDTO user = UserMapper.toUserDto((currentUser));

        Optional<GioHangSanPham> rs = gioHangSanPhamRepository.findById(req.getGioHangSanPhamId());
        GioHangSanPham gioHangSanPham = rs.get();

        Optional<LoaiSanPham> res = loaiSPRepository.findById(req.getLoaiSPId());
        LoaiSanPham loaiSanPham = res.get();

        gioHangSanPham.setLoaiSanPham(loaiSanPham);
        gioHangSanPham.setSoluong(req.getSoLuong());

        gioHangSanPhamRepository.save(gioHangSanPham);

        return ResponseEntity.ok("ok");
    }

}
