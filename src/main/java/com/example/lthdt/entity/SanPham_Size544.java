package com.example.lthdt.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sanpham_size544")
@Table(name = "sanpham_size544")
@IdClass(SanPham_SizeId544.class)
public class SanPham_Size544 {
    @Id
    @Column(name="sanpham_id")
    private String sanphamId;

    @Id
    @Column(name = "size")
    private int size;

    @Column(name = "quantity", nullable = false)
    private int quantity;
}
