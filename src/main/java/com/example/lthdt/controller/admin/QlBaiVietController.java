package com.example.lthdt.controller.admin;

import com.example.lthdt.entity.BaiViet;
import com.example.lthdt.entity.User;
import com.example.lthdt.repository.BaivietRepository;
import com.example.lthdt.repository.model.request.CreatePostReq;
import com.example.lthdt.service.BaiVietService;
import com.example.lthdt.service.ImageService;
import com.example.lthdt.service.impl.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class QlBaiVietController {
    @Autowired
    private BaiVietService baiVietService;

    @Autowired
    private BaivietRepository baivietRepository;

    @Autowired
    private ImageService imageService;

    @GetMapping("/admin/baiviet")
    public String getPostManagePage(Model model) {
        List<BaiViet> baiVietList = baivietRepository.findAll();
        model.addAttribute("baiVietList", baiVietList);

        return "admin/baiviet/list";
    }

    @GetMapping("/admin/baiviet/create")
    public String getPostCreatePage(Model model) {
        // Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);

        return "admin/baiviet/create";
    }

    @PostMapping("/api/admin/baiviet")
    public ResponseEntity<?> createPost(@Valid @RequestBody CreatePostReq req) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        BaiViet post = baiVietService.createPost(req, user);

        return ResponseEntity.ok(post.getId());
    }

    @GetMapping("/admin/baiviet/{id}")
    public String getPostDetailPage(Model model, @PathVariable long id) {
//        // Get list image of user
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        List<String> images = imageService.getListImageOfUser(user.getId());
        model.addAttribute("images", images);

        Optional<BaiViet> rs = baivietRepository.findById(id);
        BaiViet post = rs.get();
        model.addAttribute("post", post);

        return "admin/baiviet/detail";
    }

    @PutMapping("/api/admin/posts/{id}")
    public ResponseEntity<?> updatePost(@Valid @RequestBody CreatePostReq req, @PathVariable long id) {
        User user = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        baiVietService.updatePost(req, user, id);

        return ResponseEntity.ok("Cập nhật thành công");
    }

    @DeleteMapping("/api/admin/baiviet/{id}")
    public ResponseEntity<?> updatePost( @PathVariable long id) {
        baiVietService.deletePost(id);

        return ResponseEntity.ok("Xóa thành công");
    }
}
