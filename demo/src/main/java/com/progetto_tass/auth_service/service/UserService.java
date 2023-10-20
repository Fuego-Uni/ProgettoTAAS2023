package com.film_Serietv.demo.service;

import com.film_Serietv.demo.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity user);
}