package com.example.lthdt.entity;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="image544")
@Table(name = "image544")
public class Image544 {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private long size;

    @Column(name = "type")
    private String type;

    @Column(name = "link", unique = true)
    private String link;

    @ManyToOne
    @JoinColumn(name = "uploaded_by")
    private User544 uploadedBy;

    @Column(name = "uploaded_at")
    private Timestamp uploadedAt;
}
