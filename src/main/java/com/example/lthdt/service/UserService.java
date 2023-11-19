package com.example.lthdt.service;

import com.example.lthdt.entity.User;
import com.example.lthdt.repository.model.request.ChangePasswordReq;
import com.example.lthdt.repository.model.request.CreateUserReq;
import com.example.lthdt.repository.model.request.UpdateProfileReq;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public User createUser(CreateUserReq req);

    public void changePassword(User user, ChangePasswordReq req);

    public User updateProfile(User user, UpdateProfileReq req);
}
