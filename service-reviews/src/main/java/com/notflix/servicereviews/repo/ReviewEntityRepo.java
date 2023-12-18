package com.notflix.servicereviews.repo;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notflix.servicereviews.Entity.ReviewEntity;

public interface ReviewEntityRepo extends JpaRepository<ReviewEntity, Long> {
  @Query("SELECT r FROM ReviewEntity r WHERE r.film.id = ?1")
  List<ReviewEntity> findByFilmId(int filmId);

  // get reviews for a user and a film
  @Query("SELECT r FROM ReviewEntity r WHERE r.film.id = ?1 AND r.user = ?2")
  Optional<ReviewEntity> findByFilmIdAndUserEmail(int filmId, String email);
}