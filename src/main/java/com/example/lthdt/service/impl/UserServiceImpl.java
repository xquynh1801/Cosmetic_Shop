package com.example.lthdt.service.impl;

import com.example.lthdt.entity.GioHang;
import com.example.lthdt.entity.User;
import com.example.lthdt.exception.BadRequestException;
import com.example.lthdt.exception.DuplicateRecordException;
import com.example.lthdt.repository.UserRepository;
import com.example.lthdt.repository.model.mapper.UserMapper;
import com.example.lthdt.repository.model.request.ChangePasswordReq;
import com.example.lthdt.repository.model.request.CreateUserReq;
import com.example.lthdt.repository.model.request.UpdateProfileReq;
import com.example.lthdt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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

    @Override
    public void changePassword(User user, ChangePasswordReq req) {
        // Validate password
        if (!BCrypt.checkpw(req.getOldPassword(), user.getMatkhau())) {
            throw new BadRequestException("Mật khẩu cũ không chính xác");
        }

        String hash = BCrypt.hashpw(req.getNewPassword(), BCrypt.gensalt(12));
        user.setMatkhau(hash);
        userRepository.save(user);
    }

    @Override
    public User updateProfile(User user, UpdateProfileReq req) {
        user.setDiachi(req.getAddress());
        user.setSdt(req.getPhone());
        user.setHoten(req.getFullName());

        return userRepository.save(user);
    }
}
