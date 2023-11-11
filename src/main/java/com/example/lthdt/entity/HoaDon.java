package com.example.lthdt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hoadon")
public class HoaDon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "phuongthucthanhtoan")
    private String phuongthucthanhtoan;

    @OneToOne
    @JoinColumn(name = "donhang_id")
    private DonHang donHang;

    @Column(name = "mota")
    private String mota;

    @Column(name = "thoigian")
    private Date thoigian;

    @Column(name = "tongtra")
    private long tongtra;

    public HoaDon(Long Id, String phuongthucthanhtoan, DonHang donHang, String mota, long tongtra){
        this.id = Id;
        this.phuongthucthanhtoan = phuongthucthanhtoan;
        this.donHang = donHang;
        this.mota = mota;
        this.tongtra=tongtra;
        this.thoigian = new Date();
    }
}
