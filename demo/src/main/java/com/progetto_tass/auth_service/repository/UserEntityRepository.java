package com.progetto_tass.auth_service.repository;

import com.progetto_tass.auth_service.entity.UserEntity;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
 Optional<UserEntity> findByEmail(String email);
}
