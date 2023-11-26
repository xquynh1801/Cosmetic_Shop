package com.example.lthdt.service;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.repository.model.dto.TrangDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DonHangService {

    public List<DonHang> adminGetListDH(String trangthai);
}
