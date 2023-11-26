package com.example.lthdt.repository;

import com.example.lthdt.entity.BaiViet;
import com.example.lthdt.entity.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaivietRepository extends JpaRepository<BaiViet, Long> {

    @Query(value ="SELECT * FROM baiviet where trangthai = 1", nativeQuery = true)
    public List<BaiViet> getBaiViet();
}
