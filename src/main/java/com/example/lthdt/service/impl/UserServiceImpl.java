package com.example.lthdt.service.impl;

import com.example.lthdt.entity.GioHang;
import com.example.lthdt.entity.User;
import com.example.lthdt.exception.DuplicateRecordException;
import com.example.lthdt.repository.UserRepository;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.repository.model.request.CreateUserReq;
import com.example.lthdt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(CreateUserReq req) {
        User user = new User();
        // Check email exist
        user = userRepository.findByEmail(req.getEmail());
        if (user != null) {
            throw new DuplicateRecordException("Email đã tồn tại trong hệ thống. Vui lòng sử dụng email khác.");
        }

        user = UserMapper.toUser(req);

        userRepository.save(user);

        return user;
    }
}
