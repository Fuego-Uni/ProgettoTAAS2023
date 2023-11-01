package com.notflix.serviceauth.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "user_entity")
public class UserEntity {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 private String name;

 private String email;

 @Column(name = "role")
 @Enumerated(EnumType.STRING)
 private UserRole role;

 //
 @ManyToMany
 @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
 private List<UserEntity> friends = new ArrayList<>();


 // setters
 public void setName(String name) {
  this.name = name;
 }
 public void setEmail(String email) {
  this.email = email;
 }
 public void setRole(UserRole role) {
  this.role = role;
 }

}
