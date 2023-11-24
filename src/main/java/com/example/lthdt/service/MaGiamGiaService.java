package com.example.lthdt.service;

import com.example.lthdt.entity.MaGiamGia;
import com.example.lthdt.repository.model.request.CreatePromotionReq;
import org.springframework.stereotype.Service;

@Service
public interface MaGiamGiaService {

    public MaGiamGia createPromotion(CreatePromotionReq req);

    public void updatePromotion(long id, CreatePromotionReq req);

    public void deletePromotion(long id);
}
