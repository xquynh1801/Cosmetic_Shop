package com.example.lthdt.service.impl.security;

import com.example.lthdt.entity.User544;
import com.example.lthdt.repository.UserRepository544;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService544 implements UserDetailsService {
    @Autowired
    private UserRepository544 userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User544 user = userRepository.findByEmail(s);
        if (user != null) {
            return new CustomUserDetails544(user);
        } else {
            throw new UsernameNotFoundException("User get email " + s + " does not exist.");
        }
    }
}
