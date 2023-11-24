package com.example.lthdt.repository.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateProductReq {
    @NotNull(message = "Tên sản phẩm trống")
    @NotEmpty(message = "Tên sản phẩm trống")
    @Size(min = 1, max = 300, message = "Độ dài tên sản phẩm từ 1 - 300 ký tự")
    @ApiModelProperty(
            example="Adidas KH422",
            notes="Tên sản phẩm trống",
            required=true
    )
    private String name;

    @NotNull(message = "Mô tả trống")
    @NotEmpty(message = "Mô tả trống")
    @ApiModelProperty(
            example="Lorem",
            notes="Mô tả trống",
            required=true
    )
    private String description;

    @NotNull(message = "Nhãn hiệu trống")
    @ApiModelProperty(
            example="1",
            notes="Nhãn hiệu trống",
            required=true
    )
    @JsonProperty("brand_id")
    private Integer brandId;

    @JsonProperty("is_available")
    private boolean isAvailable;

    @JsonProperty("product_images")
    private ArrayList<String> productImages;
}
