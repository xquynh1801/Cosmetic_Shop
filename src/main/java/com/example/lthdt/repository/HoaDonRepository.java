package com.example.lthdt.repository;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.HoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
}
