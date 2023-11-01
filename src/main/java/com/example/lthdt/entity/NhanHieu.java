package com.example.lthdt.entity;

import javax.persistence.*;

import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "nhanHieuDTO",
                        classes = @ConstructorResult(
                                targetClass = NhanHieuDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Integer.class),
                                        @ColumnResult(name = "name", type = String.class),
                                        @ColumnResult(name = "image", type = String.class),
                                        @ColumnResult(name = "product_count", type = Integer.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getListBrandAndProductCount",
        resultSetMapping = "nhanHieuDTO",
        query = "SELECT nhanhieu.id, nhanhieu.name, nhanhieu.image,\n" +
                "(\n" +
                "    SELECT count(sanpham.id)\n" +
                "    FROM sanpham\n" +
                "    WHERE sanpham.nhanhieu_id = nhanhieu.id\n" +
                ") product_count \n" +
                "FROM nhanhieu "
)

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "nhanhieu")
@Table(name = "nhanhieu")
public class NhanHieu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "nhanHieu", cascade = CascadeType.ALL)
    private List<SanPham> sanPhams;
}
