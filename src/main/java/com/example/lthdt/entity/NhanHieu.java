package com.example.lthdt.entity;

import javax.persistence.*;

import com.example.lthdt.repository.model.dto.NhanHieuDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "nhanhieu")
@Table(name = "nhanhieu")
public class NhanHieu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "mota")
    private String mota;

    @OneToMany(mappedBy = "nhanHieu", cascade = CascadeType.ALL)
    private List<SanPham> sanPhams;
}
