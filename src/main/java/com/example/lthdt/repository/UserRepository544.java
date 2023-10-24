package com.example.lthdt.repository;

import com.example.lthdt.entity.User544;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository544 extends JpaRepository<User544, Long> {
    User544 findByEmail(String email);

//    @Query("select u from users544 u where u.facebookUid = ?1")
//    Optional<User544> getUserByFacebookUid(String facebookUid);
}
