package com.example.lthdt.repository;

import com.example.lthdt.entity.DonHang;
import com.example.lthdt.entity.SanPhamMua;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamMuaRepository extends JpaRepository<SanPhamMua, Long> {
    public int countByLoaiSanPhamMua_Id(Long id);
}
