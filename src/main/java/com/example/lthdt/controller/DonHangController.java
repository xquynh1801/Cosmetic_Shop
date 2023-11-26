package com.example.lthdt.controller;

import com.example.lthdt.entity.*;
import com.example.lthdt.repository.*;
import com.example.lthdt.repository.model.dto.GioHangSanPhamDTO;
import com.example.lthdt.repository.model.dto.UserDTO;
import com.example.lthdt.repository.model.mapper.GioHangSanPhamMapper;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.repository.model.request.CheckCreateOrderRequest;
import com.example.lthdt.repository.model.request.CheckMaGiamGiaRequest;
import com.example.lthdt.repository.model.request.CheckMaGiamGiaResponse;
import com.example.lthdt.repository.model.request.CreateOrderRequest;
import com.example.lthdt.service.GioHangSanPhamService;
import com.example.lthdt.service.GioHangService;
import com.example.lthdt.service.SanPhamService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DonHangController {
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private SanPhamRepository sanPhamRepository;

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

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private MaGiamGiaRepository maGiamGiaRepository;

    @Autowired
    private SanPhamMuaRepository sanPhamMuaRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @PostMapping("/check_create_order")
    public ResponseEntity<?> checkCreateOrder(@Valid @RequestBody CheckCreateOrderRequest req) throws IOException {
        // Lấy người dùng hiện đang đăng nhập (sử dụng Spring Security)
        User currentUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserDTO user = UserMapper.toUserDto((currentUser));

        List<GioHangSanPham> result = gioHangSanPhamService.findByCartId(req.getCart_id());

        List<GioHangSanPhamDTO> listCartProduct = new ArrayList<>();
        for(GioHangSanPham s:result){
            listCartProduct.add(GioHangSanPhamMapper.toGioHangSanPhamDTO(s));
        }

        for(GioHangSanPhamDTO dto:listCartProduct){
            if(dto.getLoaiSP().getSoluong() < dto.getSoluong()){
                return ResponseEntity.ok("not ok");
            }
        }

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/create_order")
    public String createOrder(Model model){
        // Lấy người dùng hiện đang đăng nhập (sử dụng Spring Security)
        User currentUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserDTO user = UserMapper.toUserDto((currentUser));

        GioHang gioHang = gioHangService.findByUserId(user.getId());

        List<GioHangSanPham> result = gioHangSanPhamService.findByCartId(gioHang.getId());

        long tong_tien=0;
        List<GioHangSanPhamDTO> listCartProduct = new ArrayList<>();
        for(GioHangSanPham s:result){
            listCartProduct.add(GioHangSanPhamMapper.toGioHangSanPhamDTO(s));
            tong_tien += (s.getLoaiSanPham().getGia()*s.getSoluong());
        }

        model.addAttribute("listCartProduct", listCartProduct);
        model.addAttribute("user", user);
        model.addAttribute("tong_tien", tong_tien);

        return "shop/createOrder";
    }

    @PostMapping("/api/check-magiamgia")
    public ResponseEntity<?> checkMaGiamGia(@Valid @RequestBody CheckMaGiamGiaRequest req) {
        CheckMaGiamGiaResponse checkMaGiamGiaResponse = new CheckMaGiamGiaResponse();

        MaGiamGia maGiamGia = maGiamGiaRepository.findByCode(req.getMagiamgia());
        if (maGiamGia == null || maGiamGia.equals("")) {
            checkMaGiamGiaResponse.setThongbao("Nhập sai mã giảm giá");
            checkMaGiamGiaResponse.setMax_giatri(0);
        } else {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp expiredAt = maGiamGia.getExpiredAt();
            if(now.after(expiredAt)){
                checkMaGiamGiaResponse.setThongbao("Mã giảm giá hết hạn");
                checkMaGiamGiaResponse.setMax_giatri(0);
            }else if(maGiamGia.getSoluong() < 1){
                checkMaGiamGiaResponse.setThongbao("Mã giảm giá hết lượt sử dụng");
                checkMaGiamGiaResponse.setMax_giatri(0);
            }else {
                checkMaGiamGiaResponse.setThongbao("Áp dụng mã giảm giá thành công");
                checkMaGiamGiaResponse.setMax_giatri(maGiamGia.getMax_giatri());
            }
        }

        return ResponseEntity.ok(checkMaGiamGiaResponse);
    }

    @PostMapping("/saveOrder")
    public ResponseEntity<?> saveOrder(@Valid @RequestBody CreateOrderRequest req) throws IOException {
        // Lấy người dùng hiện đang đăng nhập (sử dụng Spring Security)
        User currentUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserDTO user = UserMapper.toUserDto((currentUser));

        GioHang gioHang = gioHangService.findByUserId(user.getId());

        List<GioHangSanPham> result = gioHangSanPhamService.findByCartId(gioHang.getId());

        List<GioHangSanPhamDTO> listCartProduct = new ArrayList<>();
        for(GioHangSanPham s:result){
            listCartProduct.add(GioHangSanPhamMapper.toGioHangSanPhamDTO(s));
        }

        for(GioHangSanPhamDTO dto:listCartProduct){
            if(dto.getLoaiSP().getSoluong() < dto.getSoluong()){
                return ResponseEntity.ok("not ok");
            }
        }

        DonHang donHang = new DonHang();
        donHang.setTenNguoiNhan(req.getReceiver_name());
        donHang.setSdtNguoiNhan(req.getReceiver_phone());
        donHang.setDiachiNguoiNhan(req.getReceiver_address());
        donHang.setTonggia(req.getPrice());
        donHang.setGiamgia(req.getGiamgia());
        donHang.setPhigiaohang("Miễn phí");
        donHang.setTongtra(req.getTotal_price());
        donHang.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        donHang.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        donHang.setNguoidat(currentUser);
        donHang.setPhuongthucthanhtoan("Thanh toán khi nhận hàng");
        donHang.setTrangthai(0);
        donHang.setIsPaid(0);
        donHangRepository.save(donHang);


        for(GioHangSanPham s:result){
            SanPhamMua sanPhamMua = new SanPhamMua();
            sanPhamMua.setSoluongmua(s.getSoluong());
            sanPhamMua.setDonHang(donHang);
            sanPhamMua.setLoaiSanPhamMua(s.getLoaiSanPham());
            sanPhamMuaRepository.save(sanPhamMua);

            loaiSPRepository.update(((s.getLoaiSanPham().getSoluong()) - s.getSoluong()), s.getLoaiSanPham().getId());

            SanPham sp = s.getLoaiSanPham().getSanPhamLoai();
            sp.setTong_ban(sp.getTong_ban()+s.getSoluong());
            sanPhamRepository.save(sp);
        }

        MaGiamGia maGiamGia = maGiamGiaRepository.findByCode(req.getMagiamgia());
        if (maGiamGia != null) {
            long sl = maGiamGia.getSoluong() - 1;
            maGiamGiaRepository.update(sl, maGiamGia.getId());
        }

        gioHangSanPhamRepository.delete(gioHang.getId());

        return ResponseEntity.ok("ok");
    }
}
