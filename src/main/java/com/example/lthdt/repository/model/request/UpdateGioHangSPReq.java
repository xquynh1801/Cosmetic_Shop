package com.example.lthdt.repository.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateGioHangSPReq {
    private long gioHangSanPhamId;

    private long loaiSPId;

    private int soLuong;
}
