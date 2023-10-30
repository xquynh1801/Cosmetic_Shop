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

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "sanPhamDTO",
                        classes = @ConstructorResult(
                                targetClass = SanPhamDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = String.class),
                                        @ColumnResult(name = "ten", type = String.class),
                                        @ColumnResult(name = "slug", type = String.class),
                                        @ColumnResult(name = "tong_ban", type = Integer.class),
                                        @ColumnResult(name = "image", type = String.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListNewProduct",
        resultSetMapping = "sanPhamDTO",
        query = "SELECT sp.id, sp.ten, sp.slug, sp.tong_ban, sp.product_images as image \n" +
                "FROM sanpham sp\n" +
                "WHERE sp.is_available = true\n" +
                "LIMIT ?1 \n"
)
@NamedNativeQuery(
        name = "getAllProduct",
        resultSetMapping = "sanPhamDTO",
        query = "SELECT sp.id, sp.ten, sp.slug, sp.tong_ban, sp.product_images as image \n" +
                "FROM sanpham sp\n"
)
@NamedNativeQuery(
        name = "getListBestSellerProduct",
        resultSetMapping = "sanPhamDTO",
        query = "SELECT sp.id, sp.ten, sp.slug, sp.tong_ban, sp.product_images as image \n" +
                "FROM sanpham sp\n" +
                "WHERE sp.is_available = true\n" +
                "LIMIT ?1 \n"
)
@NamedNativeQuery(
        name = "searchProductByKeyword",
        resultSetMapping = "sanPhamDTO",
        query = "SELECT DISTINCT sp.id, sp.ten, sp.slug, sp.tong_ban, sp.product_images as image\n" +
                "FROM sanpham sp\n" +
                "WHERE sp.is_available = true AND (sp.ten LIKE CONCAT('%',:keyword,'%'))\n" +
                "LIMIT :limit\n" +
                "OFFSET :offset"
)

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

    @Column(name = "slug", nullable = false)
    private String slug;

    @Column(name = "mota", columnDefinition = "TEXT")
    private String mota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nhanhieu_id")
    private NhanHieu nhanHieu;

    @OneToMany(mappedBy = "sanPhamSize")
    List<LoaiSanPham> loaiSanPhams;

    @Column(name = "tong_ban")
    private int tong_ban;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "is_available", columnDefinition = "TINYINT(1)")
    private boolean isAvailable;

    @Convert(converter = StringListConverter.class, attributeName = "productImages")
    @Column(name = "product_images")
    private List<String> productImages;

}

