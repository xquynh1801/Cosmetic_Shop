package com.example.lthdt.repository.model.request;

import com.example.lthdt.repository.model.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ThongKeResponse {
    private List<UserDTO> userList;

    private long tongdoanhthu;

    private Date batdau;

    private Date ketthuc;

}
