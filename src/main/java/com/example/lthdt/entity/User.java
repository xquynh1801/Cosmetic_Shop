package com.example.lthdt.entity;

import com.example.lthdt.entity.converter.StringListConverter;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "hoten", nullable = false, length = 200)
    private String hoten;

    @Column(name = "email", unique = true, length = 200)
    private String email;

    @Column(name = "matkhau")
    private String matkhau;

    @Convert(converter = StringListConverter.class)
    @Column(name = "roles", nullable = false, columnDefinition = "json")
    private List<String> roles;

    @Column(name = "diachi")
    private String diachi;

    @Column(name = "sdt")
    private String sdt;

    @Column(name = "trangthai", columnDefinition = "BOOLEAN")
    private boolean trangthai;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private GioHang gioHang;

    @OneToMany(mappedBy = "uploadedBy", cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<BaiViet> baiViets;

    public User(long id) {
        this.id = id;
    }
}
