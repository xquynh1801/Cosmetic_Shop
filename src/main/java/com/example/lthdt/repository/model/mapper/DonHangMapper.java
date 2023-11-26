package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.SanPhamMua;
import com.example.lthdt.repository.model.dto.DonHangDTO;
import com.example.lthdt.repository.model.dto.SanPhamMuaDTO;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DonHangMapper {
    public static DonHangDTO toDonHangDTO(DonHang donHang){
        List<SanPhamMuaDTO> sanPhamMuaDTOS = new ArrayList<>();
        List<SanPhamMua> sanPhamMuas = donHang.getSanPhamMuas();
        for (SanPhamMua spm:sanPhamMuas){
            sanPhamMuaDTOS.add(SanPhamMuaMapper.toSanPhamMuaDTO(spm));
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = donHang.getCreatedAt().toLocalDateTime().format(formatter);
        String formattedDate2 = donHang.getModifiedAt().toLocalDateTime().format(formatter);

        return new DonHangDTO(
                donHang.getId(),
                donHang.getTenNguoiNhan(),
                donHang.getSdtNguoiNhan(),
                donHang.getDiachiNguoiNhan(),
                UserMapper.toUserDto(donHang.getNguoidat()),
                sanPhamMuaDTOS,
                donHang.getTonggia(),
                donHang.getGiamgia(),
                donHang.getPhigiaohang(),
                donHang.getTongtra(),
                formattedDate,
                formattedDate2,
                donHang.getPhuongthucthanhtoan(),
                donHang.getTrangthai(),
                donHang.getIsPaid());
    }
}
