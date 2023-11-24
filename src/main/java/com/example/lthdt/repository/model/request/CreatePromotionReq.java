package com.example.lthdt.repository.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatePromotionReq {
    @NotNull(message = "Mã code rỗng")
    @NotEmpty(message = "Mã code rỗng")
    @Pattern(regexp="^[0-9A-Z-]+$", message = "Mã code không đúng định dạng")
    private String code;

    @NotNull(message = "Tên rỗng")
    @NotEmpty(message = "Tên rỗng")
    @Size(min = 1, max = 300, message = "Độ dài tên từ 1 - 300 kí tự")
    private String name;

    @Min(1)
    @Max(2)
    private int discount_type;

    private long discount_value;

    private long max_value;

    private int soluong;

    private boolean active;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private Timestamp expired_date;
}
