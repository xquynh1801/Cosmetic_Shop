package com.example.lthdt.repository;

import com.example.lthdt.entity.GioHangSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GioHangSanPhamRepository extends JpaRepository<GioHangSanPham, Long> {
    @Query(nativeQuery = true, value ="SELECT * FROM sanpham_giohang where giohang_id=?1")
    public List<GioHangSanPham> findByCartId(Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE sanpham_giohang SET soluong = ?1 WHERE id = ?2", nativeQuery = true)
    public int update(int soluong, Long id);


    @Query(nativeQuery = true, value ="SELECT * FROM sanpham_giohang where loaisanpham_id=?1 and giohang_id=?2")
    public GioHangSanPham findByLoaiIdAndGioId(Long idLoai, Long idGio);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM sanpham_giohang WHERE giohang_id = ?1", nativeQuery = true)
    public int delete(Long id);

}
