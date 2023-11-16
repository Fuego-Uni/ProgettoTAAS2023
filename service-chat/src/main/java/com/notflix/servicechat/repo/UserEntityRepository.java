package com.notflix.servicechat.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notflix.servicechat.Entity.UserEntity;

public interface UserEntityRepository extends JpaRepository<UserEntity, String> {
  Optional<UserEntity> findByEmail(String email);
}
