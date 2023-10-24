package com.example.lthdt.entity;

import com.example.lthdt.entity.converter.StringListConverter;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users544")
public class User544 {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "full_name", nullable = false, length = 200)
    private String fullName;

    @Column(name = "email", unique = true, length = 200)
    private String email;

    @Column(name = "password")
    private String password;

    @Convert(converter = StringListConverter.class)
    @Column(name = "roles", nullable = false, columnDefinition = "json")
    private List<String> roles;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status", columnDefinition = "BOOLEAN")
    private boolean status;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public User544(long id) {
        this.id = id;
    }
}
