package com.example.lthdt.service;

import com.example.lthdt.entity.User;
import com.example.lthdt.repository.model.request.CreateUserReq;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User createUser(CreateUserReq req);
}
