package com.example.lthdt.repository.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrangDTO {
    private Object items;

    private int totalPages;

    private int currentPage;
}
