package com.progetto_tass.auth_service.service;

import com.progetto_tass.auth_service.entity.UserEntity;

import java.util.Optional;

public interface UserService {

    Optional<UserEntity> findByEmail(String email);

    void save(UserEntity user);
}