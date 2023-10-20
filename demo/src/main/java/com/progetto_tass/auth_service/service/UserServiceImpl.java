package com.film_Serietv.demo.service;

import com.film_Serietv.demo.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.film_Serietv.demo.entity.UserEntity;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements com.film_Serietv.demo.service.UserService {

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