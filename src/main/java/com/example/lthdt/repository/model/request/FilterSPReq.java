package com.example.lthdt.repository.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FilterSPReq {
    private List<Integer> brands;

    @JsonProperty("min_price")
    private Long minPrice;

    @JsonProperty("max_price")
    private Long maxPrice;

    private int page;
}
