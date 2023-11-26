package com.example.lthdt.repository;

import com.example.lthdt.entity.Image;
import com.example.lthdt.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    @Query(nativeQuery = true, value = "SELECT link FROM image WHERE uploaded_by = ?1")
    public List<String> getListImageOfUser(long userId);

    public Image findByLink(String link);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM baiviet WHERE image = ?1 " +
            "UNION ALL " +
            "SELECT COUNT(*) FROM nhanhieu WHERE image = ?1 " +
            "UNION ALL " +
            "SELECT COUNT(*) FROM sanpham WHERE FIND_IN_SET(?1, REPLACE(product_images, ',', '')) > 0")
    public Integer checkImgInUse(String link);
}
