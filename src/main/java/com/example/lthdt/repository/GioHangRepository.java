package com.example.lthdt.repository;

import com.example.lthdt.entity.GioHang;
import com.example.lthdt.entity.GioHangSanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Long> {
    @Query(nativeQuery = true, value ="SELECT * \n" +
            "FROM giohang where user_id in (?1)\n")
    public GioHang findByUserId(Long id);

}
