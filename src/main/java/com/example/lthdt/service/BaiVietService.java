package com.example.lthdt.service;

import com.example.lthdt.entity.BaiViet;
import com.example.lthdt.entity.User;
import com.example.lthdt.repository.model.request.CreatePostReq;
import org.springframework.stereotype.Service;

@Service
public interface BaiVietService {

    public BaiViet createPost(CreatePostReq req, User user);

    public void updatePost(CreatePostReq req, User user, long id);

    public void deletePost(long id);
}
