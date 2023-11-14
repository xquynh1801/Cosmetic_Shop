package com.example.lthdt.repository;

import com.example.lthdt.entity.MaGiamGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaGiamGiaRepository extends JpaRepository<MaGiamGia, Long> {
    @Query(nativeQuery = true, value ="SELECT * FROM magiamgia WHERE code = ?1 AND is_active = 1")
    public MaGiamGia findByCode(String code);
}
