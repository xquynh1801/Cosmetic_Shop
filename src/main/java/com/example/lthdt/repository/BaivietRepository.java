package com.example.lthdt.repository;

import com.example.lthdt.entity.BaiViet;
import com.example.lthdt.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaivietRepository extends JpaRepository<BaiViet, Long> {
}
