package com.example.lthdt.service.impl;

import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.exception.BadRequestException;
import com.example.lthdt.exception.InternalServerException;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.GioHangSanPhamRepository;
import com.example.lthdt.repository.LoaiSPRepository;
import com.example.lthdt.repository.SanPhamMuaRepository;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.mapper.LoaiSPMapper;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.repository.model.request.UpdateLoaiSP;
import com.example.lthdt.service.LoaiSPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LoaiSPServiceImpl implements LoaiSPService {
    @Autowired
    private LoaiSPRepository loaiSPRepository;

    @Autowired
    private SanPhamMuaRepository sanPhamMuaRepository;

    @Autowired
    private GioHangSanPhamRepository gioHangSanPhamRepository;


//    @Override
//    public List<LoaiSPDTO> findLoaiSPtheoSanPhamID(String id) {
//        List<LoaiSanPham> loais = loaiSPRepository.findLoaiSPtheoSanPhamID(id);
//        List<LoaiSPDTO> loaisps = new ArrayList<>();
//        for(LoaiSanPham s:loais){
//            loaisps.add(LoaiSPMapper.toLoaiSPDTO(s));
//        }
//        return loaisps;
//    }
//
//    @Override
//    public List<LoaiSPDTO> findLoaiSPtheoSanPhamIDvaKhoangGia(String id, Long minPrice, Long maxPrice) {
//        List<LoaiSanPham> loais = loaiSPRepository.findLoaiSPtheoSanPhamIDvaKhoangGia(id, minPrice, maxPrice);
//        List<LoaiSPDTO> loaisps = new ArrayList<>();
//        for(LoaiSanPham s:loais){
//            loaisps.add(LoaiSPMapper.toLoaiSPDTO(s));
//        }
//        return loaisps;
//    }

    @Override
    public void updateLoaiSP(UpdateLoaiSP req) {
        Optional<LoaiSanPham> rs = loaiSPRepository.findById(req.getId());
        LoaiSanPham loaiSanPham = rs.get();
        loaiSanPham.setTenloai(req.getTenloai());
        loaiSanPham.setGia(req.getGia());
        loaiSanPham.setSoluong(req.getSoluong());
        loaiSPRepository.save(loaiSanPham);
    }

    @Override
    public void deleteLoaiSP(Long id) {
        // Check product exist
        Optional<LoaiSanPham> rs = loaiSPRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Loại sản phẩm không tồn tại");
        }

        // If have order, can't delete
        int countOrder=0;
        countOrder = sanPhamMuaRepository.countByLoaiSanPhamMua_Id(rs.get().getId());

        if (countOrder > 0) {
            throw new BadRequestException("Loại sản phẩm đã được đặt hàng không thể xóa");
        }

        try {
            gioHangSanPhamRepository.deleteGioHangSanPhamsByLoaiSanPham_Id(rs.get().getId());

            loaiSPRepository.deleteById(id);
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new InternalServerException("Lỗi khi xóa loại sản phẩm");
        }
    }
}
