package com.example.lthdt.entity;

import com.example.lthdt.entity.converter.StringListConverter;
import javax.persistence.*;

import com.example.lthdt.repository.model.dto.SanPhamDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sanpham")
@Table(name = "sanpham")
public class SanPham {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "ten", nullable = false, length = 300)
    private String ten;

    @Column(name = "mota", columnDefinition = "TEXT")
    private String mota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhanhieu_id")
    private NhanHieu nhanHieu;

    @OneToMany(mappedBy = "sanPhamLoai")
    List<LoaiSanPham> loaiSanPhams;

    @Column(name = "tong_ban")
    private int tong_ban;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_available", columnDefinition = "TINYINT(1)")
    private boolean isAvailable;

    @Convert(converter = StringListConverter.class, attributeName = "productImages")
    @Column(name = "product_images", columnDefinition = "TEXT")
    private List<String> productImages;

    public SanPham(String id) {
        this.id = id;
        this.nhanHieu = new NhanHieu(Integer.parseInt(id));
        this.loaiSanPhams = new ArrayList<>();
    }
}

