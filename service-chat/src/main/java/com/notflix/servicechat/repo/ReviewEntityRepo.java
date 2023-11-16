package com.notflix.servicechat.repo;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.notflix.servicechat.Entity.ReviewEntity;

public interface ReviewEntityRepo extends JpaRepository<ReviewEntity, Long> {
    List<ReviewEntity> findByFilmFilmId(Long filmId);
}   