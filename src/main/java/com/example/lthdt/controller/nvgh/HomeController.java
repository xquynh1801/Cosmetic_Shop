package com.example.lthdt.controller.nvgh;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/nvgh")
    public String getDashboardPage(Model model) {
        return "nvgh/dashboard";
    }
}
