package com.notflix.serviceauth.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_entity")
public class UserEntity {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String name;

 private String email;

 @Column(name = "role")
 @Enumerated(EnumType.STRING)
 private UserRole role;

}
