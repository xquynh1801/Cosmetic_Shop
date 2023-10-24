package com.example.lthdt.entity;

import com.example.lthdt.entity.converter.StringListConverter;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sanpham544")
@Table(name = "sanpham544")
public class SanPham544 {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false, length = 300)
    private String name;

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhanhang_id")
    private NhanHang544 nhanHang544;

    @Column(name = "price")
    private long price;

    @Column(name = "total_sold")
    private int totalSold;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_available", columnDefinition = "TINYINT(1)")
    private boolean isAvailable;

    @Convert(converter = StringListConverter.class)
    @Column(name = "product_images", columnDefinition = "json")
    private List<String> productImages;

    @Convert(converter = StringListConverter.class)
    @Column(name = "onfeet_images", columnDefinition = "json")
    private List<String> onfeetImages;

}

