package com.example.lthdt.controller;

import com.example.lthdt.entity.BaiViet;
import com.example.lthdt.repository.BaivietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class BaiVietController {
    @Autowired
    private BaivietRepository baivietRepository;

    @GetMapping("/tin-tuc")
    public String getBlogPage(Model model) {
        List<BaiViet> baiVietList = baivietRepository.findAll();
        model.addAttribute("baiVietList", baiVietList);

        return "tintuc/blog";
    }

    @GetMapping("/chinh-sach")
    public String getPolicyPage(Model model) {
        return "blog/policy";
    }

    @GetMapping("/dieu-khoan")
    public String getRulesPage(Model model) {
         return "blog/rules";
    }

    @GetMapping("/hoi-dap")
    public String getQAPage(Model model) {
        return "blog/faqs";
    }
}
