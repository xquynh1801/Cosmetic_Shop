package com.example.lthdt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="sanpham_giohang")
@Table(name = "sanpham_giohang")
public class GioHangSanPham {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "giohang_id")
    GioHang gioHang;

    @ManyToOne
    @JoinColumn(name = "loaisanpham_id")
    LoaiSanPham loaiSanPham;

    @Column(name = "damua")
    private boolean daMua;

    @Column(name = "soluong", nullable = false)
    private int soluong;
}
