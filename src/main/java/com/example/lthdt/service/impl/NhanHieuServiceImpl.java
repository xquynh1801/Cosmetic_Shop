package com.example.lthdt.service.impl;

import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.entity.SanPham;
import com.example.lthdt.exception.BadRequestException;
import com.example.lthdt.exception.InternalServerException;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.NhanHieuRepository;
import com.example.lthdt.repository.SanPhamRepository;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import com.example.lthdt.repository.model.mapper.NhanHieuMapper;
import com.example.lthdt.repository.model.mapper.SanPhamMapper;
import com.example.lthdt.repository.model.request.CreateBrandReq;
import com.example.lthdt.service.NhanHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class NhanHieuServiceImpl implements NhanHieuService {
    @Autowired
    private NhanHieuRepository nhanHieuRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<NhanHieuDTO> getListBrand() {
        List<NhanHieu> nhanhieus = nhanHieuRepository.findAll();
        List<NhanHieuDTO> nh = new ArrayList<>();
        for(NhanHieu n:nhanhieus){
            nh.add(NhanHieuMapper.toNhanHieuDTO(n));
        }
        return nh;
    }

    @Override
    public NhanHieu createBrand(CreateBrandReq req) {
        NhanHieu brand = new NhanHieu();
        brand.setName(req.getName());
        brand.setImage(req.getImg());

        nhanHieuRepository.save(brand);

        return brand;
    }
    @Override
    public void updateBrand(int id, CreateBrandReq req) {
        // Check brand exist
        Optional<NhanHieu> rs = nhanHieuRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Nhãn hiệu không tồn tại");
        }

        NhanHieu brand = rs.get();
        brand.setName(req.getName());
        brand.setImage(req.getImg());

        try {
            nhanHieuRepository.save(brand);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi chỉnh sửa nhãn hiệu");
        }
    }

    @Override
    public void deleteBrand(int id) {
        // Check category exist
        Optional<NhanHieu> rs = nhanHieuRepository.findById(id);
        NhanHieu nhanHieu = rs.get();
        if (rs.isEmpty()) {
            throw new NotFoundException("Nhãn hiệu không tồn tại");
        }

        // Check product in brand
        int count = nhanHieu.getSanPhams().size();
        if (count > 0) {
            throw new BadRequestException("Có sản phẩm thuộc nhãn hiệu không thể xóa");
        }

        try {
            nhanHieuRepository.delete(nhanHieu);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa nhãn hiệu");
        }
    }

}
