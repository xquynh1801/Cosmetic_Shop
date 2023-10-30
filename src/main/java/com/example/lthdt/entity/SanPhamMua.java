package com.example.lthdt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="sanphammua")
@Table(name = "sanphammua")
public class SanPhamMua {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "loaisanpham_id")
    LoaiSanPham loaiSanPhamMua;

    @ManyToOne
    @JoinColumn(name = "donhang_id")
    DonHang donHang;

    @Column(name = "soluongmua")
    private int soluongmua;
}
