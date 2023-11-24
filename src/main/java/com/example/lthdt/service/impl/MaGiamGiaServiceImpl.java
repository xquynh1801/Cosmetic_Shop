package com.example.lthdt.service.impl;

import com.example.lthdt.entity.MaGiamGia;
import com.example.lthdt.exception.BadRequestException;
import com.example.lthdt.exception.InternalServerException;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.MaGiamGiaRepository;
import com.example.lthdt.repository.model.request.CreatePromotionReq;
import com.example.lthdt.service.MaGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class MaGiamGiaServiceImpl implements MaGiamGiaService {

    @Autowired
    private MaGiamGiaRepository maGiamGiaRepository;

    @Override
    public MaGiamGia createPromotion(CreatePromotionReq req) {
        // Validate
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (req.getExpired_date().before(now)) {
            throw new BadRequestException("Hạn khuyến mãi không hợp lệ");
        }
        if (req.getDiscount_type() == 2) {
            if (req.getDiscount_value() < 1 || req.getDiscount_value() > 100) {
                throw new BadRequestException("Mức giảm giá từ 1% - 100%");
            }
            if (req.getMax_value() < 1000) {
                throw new BadRequestException("Mức giảm tối đa phải lớn hơn 1000");
            }
        } else {
            if (req.getDiscount_value() < 1000) {
                throw new BadRequestException("Mức giảm giá phải lớn hơn 1000 ");
            }
        }
        MaGiamGia maGiamGia = maGiamGiaRepository.findByCode(req.getCode());
        if (maGiamGia != null) {
            throw new BadRequestException("Mã code đã tồn tại trong hệ thống");
        }

        MaGiamGia newmaGiamGia = new MaGiamGia();
        newmaGiamGia.setCode(req.getCode());
        newmaGiamGia.setTen(req.getName());
        newmaGiamGia.setSoluong(req.getSoluong());
        newmaGiamGia.setGiatri(req.getDiscount_value());
        newmaGiamGia.setMax_giatri(req.getMax_value());
        newmaGiamGia.setLoai(req.getDiscount_type());
        newmaGiamGia.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        newmaGiamGia.setExpiredAt(req.getExpired_date());
        newmaGiamGia.setActive(req.isActive());

        maGiamGiaRepository.save(newmaGiamGia);

        return newmaGiamGia;
    }

    @Override
    public void updatePromotion(long id, CreatePromotionReq req) {
        // Check exist promotion
        Optional<MaGiamGia> rs = maGiamGiaRepository.findById(id);
        MaGiamGia maGiamGia = rs.get();
        if (rs.isEmpty()) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }

        // Validate
        if (req.getDiscount_type() == 2) {
            if (req.getDiscount_value() < 1 || req.getDiscount_value() > 100) {
                throw new BadRequestException("Mức giảm giá từ 1% - 100%");
            }
            if (req.getMax_value() < 1000) {
                throw new BadRequestException("Mức giảm tối đa phải lớn hơn 1000");
            }
        } else {
            if (req.getDiscount_value() < 1000) {
                throw new BadRequestException("Mức giảm giá phải lớn hơn 1000 ");
            }
        }
        MaGiamGia mgg = maGiamGiaRepository.findByCode(req.getCode());
        if (mgg != null && mgg.getId() != id) {
            throw new BadRequestException("Mã code đã tồn tại trong hệ thống");
        }

        maGiamGia.setCode(req.getCode());
        maGiamGia.setTen(req.getName());
        maGiamGia.setSoluong(req.getSoluong());
        maGiamGia.setGiatri(req.getDiscount_value());
        maGiamGia.setMax_giatri(req.getMax_value());
        maGiamGia.setLoai(req.getDiscount_type());
        maGiamGia.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        maGiamGia.setExpiredAt(req.getExpired_date());
        maGiamGia.setActive(req.isActive());

        try {
            maGiamGiaRepository.save(maGiamGia);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật khuyến mãi");
        }
    }

    @Override
    public void deletePromotion(long id) {
        // Check exist promotion
        Optional<MaGiamGia> rs = maGiamGiaRepository.findById(id);
        MaGiamGia maGiamGia = rs.get();
        if (rs.isEmpty()) {
            throw new NotFoundException("Khuyến mãi không tồn tại");
        }

        try {
            maGiamGiaRepository.deleteById(id);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa khuyến mãi");
        }
    }

}
