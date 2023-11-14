package com.example.lthdt.repository;

import com.example.lthdt.entity.GioHang;
import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public interface LoaiSPRepository extends JpaRepository<LoaiSanPham, Long> {
    @Query(nativeQuery = true,name = "getDSLoaiSPTheoSanPhamId")
    public List<LoaiSPDTO> findLoaiSPtheoSanPhamID(String id);

    @Query(nativeQuery = true, name = "getDSLoaiSPTheoSanPhamIdVaKhoangGia")
    public List<LoaiSPDTO> findLoaiSPtheoSanPhamIDvaKhoangGia(String id, Long minPrice, Long maxPrice);

//    @Query(nativeQuery = true, value ="SELECT * FROM giohang where user_id in (?1)")
//    public LoaiSanPham findBy(Long id);
}
