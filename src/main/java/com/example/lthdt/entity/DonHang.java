package com.example.lthdt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="donhang")
@Table(name = "donhang")
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "tenNguoiNhan")
    private String tenNguoiNhan;

    @Column(name = "sdtNguoiNhan")
    private String sdtNguoiNhan;

    @Column(name = "diachiNguoiNhan")
    private String diachiNguoiNhan;

    @OneToMany(mappedBy = "donHang")
    List<SanPhamMua> sanPhamMuas;

    @OneToOne(mappedBy = "donHang", cascade = CascadeType.ALL)
    private HoaDon hoaDon;

    @Column(name = "tonggia")
    private long tonggia;

    @Column(name = "giamgia")
    private long giamgia;

    @Column(name = "tongtra")
    private long tongtra;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @ManyToOne
    @JoinColumn(name = "nguoidat")
    private User nguoidat;

    @ManyToOne
    @JoinColumn(name = "modified_by")
    private User modifiedBy;

    @Column(name = "trangthai")
    private int trangthai;

    @Column(name = "is_paid")
    private int isPaid;

//    @Type(type = "json")
//    @Column(name = "promotion", columnDefinition = "json")
//    private UsedPromotion promotion;

//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class UsedPromotion {
//        private String couponCode;
//
//        private int discountType;
//
//        private long discountValue;
//
//        private long maximumDiscountValue;
//    }

    @Column(name = "note")
    private String note;
}