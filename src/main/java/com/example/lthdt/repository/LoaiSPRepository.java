package com.example.lthdt.repository;

import com.example.lthdt.entity.GioHang;
import com.example.lthdt.entity.LoaiSanPham;
import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
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

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "Delete from loaisanpham where sanpham_id = ?1")
    public void deleteByProductId(String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE loaisanpham SET tenloai = ?1, gia = ?2, soluong = ?3 WHERE id = ?2", nativeQuery = true)
    public int updateLSP(String ten, Long gia, int soluong);

    @Query(value ="SELECT * FROM loaisanpham where tenloai = ?1 and sanpham_id=?2", nativeQuery = true)
    public LoaiSanPham findByTenloaiAndAndSanPhamLoai(String ten, String idSP);

    @Query(value ="SELECT sanpham_id FROM loaisanpham where id = ?1", nativeQuery = true)
    public String findSPIdByLoaiSPId(long id);
}
