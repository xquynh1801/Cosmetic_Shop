package com.example.lthdt.repository.model.dto;

import com.example.lthdt.entity.SanPhamMua;
import com.example.lthdt.entity.User;
import lombok.*;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DonHangDTO {
    private long id;

    private String tenNguoiNhan;

    private String sdtNguoiNhan;

    private String diachiNguoiNhan;

    private UserDTO nguoidat;

    private List<SanPhamMuaDTO> sanPhamMuas;

    private long tonggia;

    private long giamgia;

    private String phigiaohang;

    private long tongtra;

    private String createdAt;

    private String modifiedAt;

    private String phuongthucthanhtoan;

    private int trangthai;

    private int isPaid;

}
