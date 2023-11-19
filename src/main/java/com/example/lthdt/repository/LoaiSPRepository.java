package com.example.lthdt.repository;

import com.example.lthdt.entity.GioHang;
import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface LoaiSPRepository extends JpaRepository<LoaiSanPham, Long> {
    @Query(nativeQuery = true,name = "getDSLoaiSPTheoSanPhamId")
    public List<LoaiSPDTO> findLoaiSPtheoSanPhamID(String id);

    @Query(nativeQuery = true, name = "getDSLoaiSPTheoSanPhamIdVaKhoangGia")
    public List<LoaiSPDTO> findLoaiSPtheoSanPhamIDvaKhoangGia(String id, Long minPrice, Long maxPrice);

    @Modifying
    @Transactional
    @Query(value = "UPDATE loaisanpham SET soluong = ?1 WHERE id = ?2", nativeQuery = true)
    public int update(int soluong, Long id);
}
