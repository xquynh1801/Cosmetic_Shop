package com.example.lthdt.repository;

import com.example.lthdt.entity.SanPham;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String> {
    @Query(nativeQuery = true, name = "getListNewProduct")
    public List<SanPhamDTO> getListNewProduct(int limit);

    @Query(nativeQuery = true, name = "getListBestSellerProduct")
    public List<SanPhamDTO> getListBestSellerProduct(int limit);

    @Query(nativeQuery = true, name = "getAllProduct")
    public List<SanPhamDTO> getAllProduct();

    @Query(nativeQuery = true, name = "searchProductByKeyword")
    public List<SanPhamDTO> searchProductByKeyword(@Param("keyword") String keyword, @Param("limit") int limit, @Param("offset") int offset);

    @Query(nativeQuery = true, value = "SELECT count(DISTINCT sp.id)\n" +
            "FROM sanpham sp\n" +
            "WHERE sp.is_available = true AND (sp.ten LIKE CONCAT('%',:keyword,'%'))\n")
    public int countProductByKeyword(@Param("keyword") String keyword);

    @Query(nativeQuery = true, value ="SELECT * \n" +
            "FROM sanpham sp\n" +
            "WHERE sp.is_available = true\n" +
            "LIMIT ?1 \n")
    public List<SanPham> getAllAvailable(int limit);
}
