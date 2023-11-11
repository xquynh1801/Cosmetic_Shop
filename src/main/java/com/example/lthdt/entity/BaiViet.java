package com.example.lthdt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "baiviet")
@Table(name = "baiviet")
public class BaiViet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "title", nullable = false, length = 300)
    private String title;

    @Column(name = "mota", columnDefinition = "TEXT")
    private String mota;

    @Column(name = "noidung", columnDefinition = "TEXT")
    private String noidung;

    @Column(name = "image")
    private String image;

    @Column(name = "trangthai", columnDefinition = "int default 0")
    private int trangthai;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "modified_at")
    private Timestamp modifiedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modified_by")
    private User modifiedBy;
}
