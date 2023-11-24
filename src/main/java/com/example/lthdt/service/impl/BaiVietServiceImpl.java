package com.example.lthdt.service.impl;

import com.example.lthdt.entity.BaiViet;
import com.example.lthdt.entity.User;
import com.example.lthdt.exception.InternalServerException;
import com.example.lthdt.exception.NotFoundException;
import com.example.lthdt.repository.BaivietRepository;
import com.example.lthdt.repository.model.request.CreatePostReq;
import com.example.lthdt.service.BaiVietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class BaiVietServiceImpl implements BaiVietService {
    @Autowired
    private BaivietRepository baivietRepository;

    @Override
    public BaiViet createPost(CreatePostReq req, User user) {
        BaiViet post = new BaiViet();

        post.setTitle(req.getTitle());
        post.setNoidung(req.getContent());
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        post.setCreatedBy(user);
        post.setMota(req.getDescription());
        post.setImage(req.getImage());
        post.setTrangthai(req.getStatus());

        baivietRepository.save(post);

        return post;
    }

    @Override
    public void updatePost(CreatePostReq req, User user, long id) {
        Optional<BaiViet> rs = baivietRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Bài viết không tồn tại");
        }

        BaiViet post = rs.get();
        post.setTitle(req.getTitle());
        post.setNoidung(req.getContent());
        post.setModifiedAt(new Timestamp(System.currentTimeMillis()));
        post.setModifiedBy(user);
        post.setMota(req.getDescription());
        post.setImage(req.getImage());
        post.setTrangthai(req.getStatus());

        try {
            baivietRepository.save(post);
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi cập nhật bài viết");
        }
    }

    @Override
    public void deletePost(long id) {
        Optional<BaiViet> rs = baivietRepository.findById(id);
        if (rs.isEmpty()) {
            throw new NotFoundException("Bài viết không tồn tại");
        }

        try {
            baivietRepository.delete(rs.get());
        } catch (Exception ex) {
            throw new InternalServerException("Lỗi khi xóa bài viết");
        }
    }

}
