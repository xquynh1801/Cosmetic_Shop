package com.example.lthdt.repository;

import com.example.lthdt.entity.NhanHieu;
import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhanHieuRepository extends JpaRepository<NhanHieu, Integer> {

}
