package com.example.lthdt.controller;

import com.example.lthdt.entity.*;
import com.example.lthdt.exception.BadRequestException;
import com.example.lthdt.repository.DonHangRepository;
import com.example.lthdt.repository.GioHangRepository;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.DonHangDTO;
import com.example.lthdt.repository.model.dto.SanPhamMuaDTO;
import com.example.lthdt.repository.model.dto.UserDTO;
import com.example.lthdt.repository.model.mapper.DonHangMapper;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.repository.model.request.ChangePasswordReq;
import com.example.lthdt.repository.model.request.CreateUserReq;
import com.example.lthdt.repository.model.request.LoginReq;
import com.example.lthdt.repository.model.request.UpdateProfileReq;
import com.example.lthdt.service.PaymentVNPayService;
import com.example.lthdt.service.UserService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import com.example.lthdt.service.impl.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.lthdt.config.Constant.*;

@Controller
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private PaymentVNPayService paymentVNPayService;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody CreateUserReq req, HttpServletResponse response) {
        // Create user
        User result = userService.createUser(req);

        GioHang gioHang = new GioHang();
        gioHang.setUser(result);
        gioHangRepository.save((gioHang));

        // Gen token
        UserDetails principal = new CustomUserDetails(result);
        String token = jwtTokenUtil.generateToken(principal);

        // Add token to cookie to login
        Cookie cookie = new Cookie("JWT_TOKEN",token);
        cookie.setMaxAge(MAX_AGE_COOKIE);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(UserMapper.toUserDto(result));
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginReq req, HttpServletResponse response) {
        // Authenticate
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getEmail(),
                            req.getMatkhau()
                    )
            );

            // Gen token
            String token = jwtTokenUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

            // Add token to cookie to login
            Cookie cookie = new Cookie("JWT_TOKEN", token);
            cookie.setMaxAge(MAX_AGE_COOKIE);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok(UserMapper.toUserDto(((CustomUserDetails) authentication.getPrincipal()).getUser()));
        } catch (Exception ex) {
            throw new BadRequestException("Email hoặc mật khẩu không chính xác");
        }
    }

    @GetMapping("/tai-khoan/lich-su-giao-dich")
    public String getOrderHistoryPage(Model model) {
        // Get list order pending
        User currentUser = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        UserDTO user = UserMapper.toUserDto((currentUser));

        List<DonHang> donHangList = donHangRepository.getListOrderOfPersonByStatus(CXN_STATUS, user.getId());
        List<DonHangDTO> donHangDTOList = new ArrayList<>();
        for(DonHang s:donHangList){
            donHangDTOList.add(DonHangMapper.toDonHangDTO(s));
        }

        model.addAttribute("user", user);
        model.addAttribute("donHangDTOList", donHangDTOList);

        return "account/order_history";
    }

    @GetMapping("/api/get-order-list")
    public ResponseEntity<?> getListOrderByStatus(@RequestParam int status) {
        // Validate status
        if (!LIST_ORDER_STATUS.contains(status)) {
            throw new BadRequestException("Trạng thái đơn hàng không hợp lệ");
        }

        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<DonHang> orders = donHangRepository.getListOrderOfPersonByStatus(status, user.getId());
        List<DonHangDTO> donHangDTOList = new ArrayList<>();
        for(DonHang s:orders){
            donHangDTOList.add(DonHangMapper.toDonHangDTO(s));
        }

        return ResponseEntity.ok(donHangDTOList);
    }

    @GetMapping("/tai-khoan/lich-su-giao-dich/{id}")
    public String getDetailOrderPage(Model model, @PathVariable int id) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        DonHang order = donHangRepository.getOrderOfPersonById(id, user.getId());
        if (order == null) {
            return "error/404";
        }

        DonHangDTO donHangDTO = new DonHangDTO();
        donHangDTO = DonHangMapper.toDonHangDTO(order);

        model.addAttribute("user", user);
        model.addAttribute("order", donHangDTO);

        boolean canCancel;
        if (order.getTrangthai() == 0 || order.getTrangthai() == 1) {
            canCancel = true;
        } else {
            canCancel = false;
        }
        if(order.getIsPaid() == 1){
            canCancel = false;
        }

        model.addAttribute("canCancel", canCancel);

        return "account/order_detail";
    }

    @PostMapping("/api/cancel-order/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable long id) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        donHangRepository.cancel(id, user.getId());
        Optional<DonHang> result = donHangRepository.findById(id);
        DonHang donHang = result.get();
        DonHangDTO donHangDTO = DonHangMapper.toDonHangDTO(donHang);

        for(SanPhamMuaDTO sanPhamMuaDTO:donHangDTO.getSanPhamMuas()){
            loaiSPRepository.update(((sanPhamMuaDTO.getLoaiSanPhamMua().getSoluong()) + (sanPhamMuaDTO.getSoluongmua())),
                    sanPhamMuaDTO.getLoaiSanPhamMua().getId());

            Optional<SanPham> res = sanPhamRepository.findById(sanPhamMuaDTO.getSanPhamMua().getId());
            SanPham sp = res.get();
            sp.setTong_ban(sp.getTong_ban()-sanPhamMuaDTO.getSoluongmua());
            sanPhamRepository.save(sp);

        }

        return ResponseEntity.ok("Hủy đơn hàng thành công");
    }

    @GetMapping("/tai-khoan")
    public String getProfilePage(Model model) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("user", user);
        return "account/account";
    }

    @PostMapping("/api/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordReq req) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        userService.changePassword(user, req);
        return ResponseEntity.ok("Đổi mật khẩu thành công");
    }

    @PostMapping("/api/update-profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileReq req) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        user = userService.updateProfile(user, req);
        UserDetails principal = new CustomUserDetails(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return ResponseEntity.ok("Cập nhật profile thành công");
    }

    @GetMapping("/api/pay/{id}")
    public String getPaymentForID(@PathVariable Long id){
        Optional<DonHang> result = donHangRepository.findById(id);
        DonHang donHang = result.get();

        String description = donHang.getId()+"-"+"Test";
        String response = paymentVNPayService.genUrl(donHang.getTongtra(), description);
        return "redirect:"+response;
    }

    @GetMapping("/api/paySuccessfull")
    public String paymentSuccessfull(Model model,
                                     @RequestParam(value = "vnp_Amount") String vnp_Amount,
                                     @RequestParam(value = "vnp_BankCode", defaultValue = "NO") String vnp_BankCode,
                                     @RequestParam(value = "vnp_BankTranNo") String vnp_BankTranNo,
                                     @RequestParam(value = "vnp_CardType") String vnp_CardType,
                                     @RequestParam(value = "vnp_OrderInfo") String vnp_OrderInfo,
                                     @RequestParam(value = "vnp_PayDate") String vnp_PayDate,
                                     @RequestParam(value = "vnp_ResponseCode") String vnp_ResponseCode,
                                     @RequestParam(value = "vnp_TmnCode") String vnp_TmnCode,
                                     @RequestParam(value = "vnp_TransactionNo") String vnp_TransactionNo,
                                     @RequestParam(value = "vnp_TransactionStatus") String vnp_TransactionStatus,
                                     @RequestParam(value = "vnp_TxnRef") String vnp_TxnRef,
                                     @RequestParam(value = "vnp_SecureHash") String vnp_SecureHash){
        Long trans_id = Long.parseLong(vnp_TransactionNo);
        String phuongthucthanhtoan = vnp_BankCode +"-"+ vnp_BankTranNo +"-"+ vnp_CardType;
        Long tongtra = Long.parseLong(vnp_Amount)/100;
        String []info = vnp_OrderInfo.split("-");
        Long orderId = Long.parseLong(info[0]);

        Optional<DonHang> result = donHangRepository.findById(orderId);
        DonHang donHang = result.get();
        donHang.setPhuongthucthanhtoan(phuongthucthanhtoan);
        donHangRepository.save(donHang);

        HoaDon hoaDon = new HoaDon(phuongthucthanhtoan, donHang, tongtra, info[1]);
        paymentVNPayService.addHoaDon(hoaDon);
        paymentVNPayService.updateOrderPaymentStatus(orderId);

        // Render trans info
        model.addAttribute("paymentType", vnp_CardType);
        model.addAttribute("bank", vnp_BankCode);
        model.addAttribute("amount", tongtra);
        model.addAttribute("transId", trans_id);
        model.addAttribute("date", hoaDon.getThoigian().toString());
        model.addAttribute("orderId", orderId);

        return "shop/payment_succeed";
    }
}
