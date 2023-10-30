package com.example.lthdt.repository;

import com.example.lthdt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

//    @Query("select u from users544 u where u.facebookUid = ?1")
//    Optional<User544> getUserByFacebookUid(String facebookUid);
}
