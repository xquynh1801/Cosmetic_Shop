package com.example.lthdt.repository.model.mapper;

import com.example.lthdt.entity.User;
import com.example.lthdt.repository.model.dto.UserDTO;
import com.example.lthdt.repository.model.request.CreateUserReq;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;

public class UserMapper {
    public static User toUser(CreateUserReq req) {
        User user = new User();
        user.setHoten(req.getHoten());
        user.setEmail(req.getEmail());
        user.setSdt(req.getSdt());
        // Hash password using BCrypt
        if(req.getMatkhau() != null) {
            String hash = BCrypt.hashpw(req.getMatkhau(), BCrypt.gensalt(12));
            user.setMatkhau(hash);
        }

        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        user.setTrangthai(true);
        user.setRole("USER");

        return user;
    }

    public static UserDTO toUserDto(User user) {
        UserDTO tmp = new UserDTO();
        tmp.setId(user.getId());
        tmp.setHoten(user.getHoten());
        tmp.setSdt(user.getSdt());
        tmp.setEmail(user.getEmail());
        tmp.setDiachi(user.getDiachi());
        tmp.setRole(user.getRole());

        return tmp;
    }
}
