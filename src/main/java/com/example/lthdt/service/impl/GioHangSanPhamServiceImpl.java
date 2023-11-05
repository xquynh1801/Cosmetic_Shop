package com.example.lthdt.service.impl;

import com.example.lthdt.entity.GioHang;
import com.example.lthdt.entity.GioHangSanPham;
import com.example.lthdt.entity.User;
import com.example.lthdt.repository.GioHangRepository;
import com.example.lthdt.repository.GioHangSanPhamRepository;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.repository.model.dto.UserDTO;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.service.GioHangSanPhamService;
import com.example.lthdt.service.GioHangService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import com.example.lthdt.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GioHangSanPhamServiceImpl implements GioHangSanPhamService {
    @Autowired
    private GioHangSanPhamRepository gioHangSanPhamRepository;

    @Override
    public List<GioHangSanPham> findByCartId(Long id) {
        List<GioHangSanPham> cart_sp = gioHangSanPhamRepository.findByCartId(id);
        return cart_sp;
    }

    @Override
    public GioHangSanPham findByLoaiIdAndGioId(Long idLoai, Long idGio) {
        GioHangSanPham cart_pr = gioHangSanPhamRepository.findByLoaiIdAndGioId(idLoai, idGio);
        return cart_pr;
    }
}
