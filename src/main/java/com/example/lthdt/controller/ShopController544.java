package com.example.lthdt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShopController544 {
    @GetMapping("/")
    public String getIndexPage() {
        return "shop/index";
    }
}
