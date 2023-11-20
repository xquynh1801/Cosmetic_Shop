package com.example.lthdt.repository;

import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {

    @Query(nativeQuery = true, value = "SELECT DISTINCT * \n" +
                "FROM sanpham sp\n" +
                "WHERE sp.is_available = true AND (sp.ten LIKE CONCAT('%',:keyword,'%'))\n" +
                "LIMIT :limit\n" +
                "OFFSET :offset")
    public List<SanPham> searchProductByKeyword(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT count(DISTINCT sp.id)\n" +
            "FROM sanpham sp\n" +
            "WHERE sp.is_available = true AND (sp.ten LIKE CONCAT('%',:keyword,'%'))\n")
    public int countProductByKeyword(@Param("keyword") String keyword);

    @Query(nativeQuery = true, value ="SELECT * \n" +
            "FROM sanpham sp\n" +
            "WHERE sp.is_available = true\n" +
            "LIMIT ?1 \n")
    public List<SanPham> getAllAvailable(int limit);

    @Query(nativeQuery = true, value ="SELECT * \n" +
            "FROM sanpham\n")
    public List<SanPham> getAllPr();

    @Query(nativeQuery = true, value ="SELECT * \n" +
            "FROM sanpham where nhanhieu_id in (?1)\n")
    public List<SanPham> locSanPham(List<Integer> brands_id);
}
