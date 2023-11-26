package com.example.lthdt.service.impl;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.DonHangRepository;
import com.example.lthdt.repository.model.dto.TrangDTO;
import com.example.lthdt.service.DonHangService;
import com.example.lthdt.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DonHangServiceImpl implements DonHangService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Override
    public List<DonHang> adminGetListDH(String trangthai) {

        List<DonHang> donHangList = donHangRepository.adminGetListDH(trangthai);

        return donHangList;
    }
}
