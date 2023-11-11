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
    private String ten;

    @Column(name = "soluong")
    private long soluong;

    @Column(name = "giatri")
    private long giatri;

    @Column(name = "max_giatri")
    private long max_giatri;

    @Column(name = "loai")
    private int loai;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "expired_at")
    private Timestamp expiredAt;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "is_active", columnDefinition = "TINYINT(1)")
    private boolean isActive;

}
