package com.example.lthdt.repository;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.SanPhamMua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SanPhamMuaRepository extends JpaRepository<SanPhamMua, Long> {
}
