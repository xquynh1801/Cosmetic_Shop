package com.example.lthdt.service;

import com.example.lthdt.entity.GioHang;
import org.springframework.stereotype.Service;

@Service
public interface GioHangService {
    public GioHang findByUserId(Long id);
}
