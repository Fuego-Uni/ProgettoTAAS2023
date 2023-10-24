package com.progetto_tass.auth_service.service;

import com.progetto_tass.auth_service.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.progetto_tass.auth_service.entity.UserEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements com.progetto_tass.auth_service.service.UserService {

    private final UserEntityRepository userEntityRepository;

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }

    @Override
    public void save(UserEntity user) {
        userEntityRepository.save(user);
    }
}