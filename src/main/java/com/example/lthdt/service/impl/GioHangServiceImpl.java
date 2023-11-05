package com.example.lthdt.service.impl;

import com.example.lthdt.entity.GioHang;
import com.example.lthdt.repository.GioHangRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public GioHang findByUserId(Long id) {
        GioHang cart = gioHangRepository.findByUserId(id);
        return cart;
    }
}
