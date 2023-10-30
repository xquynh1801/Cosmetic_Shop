package com.example.lthdt.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "magiamgia")
@Table(name = "magiamgia")
public class MaGiamGia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false, length = 300)
    private String name;

    @Column(name = "discount_value")
    private long discountValue;

    @Column(name = "maximum_discount_value")
    private long maximumDiscountValue;

    @Column(name = "discount_type")
    private int discountType;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expired_at")
    private Timestamp expiredAt;

    @Column(name = "coupon_code", unique = true)
    private String couponCode;

    @Column(name = "is_active", columnDefinition = "TINYINT(1)")
    private boolean isActive;

    @Column(name = "is_public", columnDefinition = "TINYINT(1)")
    private boolean isPublic;

//    public MaGiamGia544(Order.UsedPromotion promotion) {
//        this.discountValue = promotion.getDiscountValue();
//        this.maximumDiscountValue = promotion.getMaximumDiscountValue();
//        this.discountType = promotion.getDiscountType();
//        this.couponCode = promotion.getCouponCode();
//    }
}
