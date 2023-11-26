package com.example.lthdt.repository.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderRequest {
    private String receiver_name;
    private String receiver_phone;
    private String receiver_address;
    private String magiamgia;
    private long price;
    private long giamgia;
    private long total_price;
}
