package com.notflix.serviceauth.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "user_entity")
public class UserEntity {
  @Id
  @Column(name = "email")
  private String email;

  @Column(name = "name")
  private String name;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @ManyToMany
  @JoinTable(name = "user_friends", joinColumns = @JoinColumn(name = "user_email"), inverseJoinColumns = @JoinColumn(name = "friend_email") )
  @JsonIgnore
  private List<UserEntity> friends = new ArrayList<>();

  public void setEmail(String email) {
    this.email = email;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public void setFriends(List<UserEntity> friends) {
    this.friends = friends;
  }
  public void addFriend(UserEntity friend) {
    this.friends.add(friend);
  }
  // getters

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public UserRole getRole() {
    return role;
  }

  public List<UserEntity> getFriends() {
    return friends;
  }

  public String getId() {
    return email;
  }
  // set name
  public void setName(String name) {
    this.name = name;
  }

}
