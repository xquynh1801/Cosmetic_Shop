package com.example.lthdt.repository.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ThongKeReq {
    private Date batDauDate;

    private Date ketThucDate;
}
