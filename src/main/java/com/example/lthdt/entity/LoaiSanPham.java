package com.example.lthdt.entity;

import javax.persistence.*;

import com.example.lthdt.repository.model.dto.LoaiSPDTO;
import com.example.lthdt.repository.model.dto.SanPhamDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@SqlResultSetMappings(
        value = {
                @SqlResultSetMapping(
                        name = "loaiSPDTO",
                        classes = @ConstructorResult(
                                targetClass = LoaiSPDTO.class,
                                columns = {
                                        @ColumnResult(name = "id", type = Long.class),
                                        @ColumnResult(name = "tenloai", type = String.class),
                                        @ColumnResult(name = "soluong", type = Integer.class),
                                        @ColumnResult(name = "gia", type = Long.class)
                                }
                        )
                )
        }
)
@NamedNativeQuery(
        name = "getDSLoaiSPTheoSanPhamId",
        resultSetMapping = "loaiSPDTO",
        query = "SELECT lsp.id, lsp.tenloai, lsp.soluong, lsp.gia \n" +
                "FROM sanpham sp join loaisanpham lsp on lsp.sanpham_id = sp.id \n" +
                "WHERE sp.is_available = true and sp.id = ?1 \n"
)
@NamedNativeQuery(
        name = "getDSLoaiSPTheoSanPhamIdVaKhoangGia",
        resultSetMapping = "loaiSPDTO",
        query = "SELECT lsp.id, lsp.tenloai, lsp.soluong, lsp.gia \n" +
                "FROM sanpham sp join loaisanpham lsp on lsp.sanpham_id = sp.id \n" +
                "WHERE sp.is_available = true and sp.id = ?1 and lsp.gia >= ?2 and lsp.gia <= ?3\n"
)

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "loaisanpham")
@Table(name = "loaisanpham")
public class LoaiSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "tenloai")
    private String tenloai;

    @Column(name = "soluong", nullable = false)
    private int soluong;

    @Column(name = "gia", nullable = false)
    private long gia;

    @ManyToOne
    @JoinColumn(name = "sanpham_id")
    private SanPham sanPhamLoai;

    @OneToMany(mappedBy = "loaiSanPham", fetch = FetchType.LAZY)
    List<GioHangSanPham> gioHangSanPhams;

    @OneToMany(mappedBy = "loaiSanPhamMua")
    List<SanPhamMua> sanPhamMuas;
}
