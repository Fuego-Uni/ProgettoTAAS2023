package com.notflix.servicereviews.repo;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notflix.servicereviews.entity.ReviewEntity;

public interface ReviewEntityRepo extends JpaRepository<ReviewEntity, Long> {
  @Query("SELECT r FROM ReviewEntity r WHERE r.media.id = ?1")
  List<ReviewEntity> findByMediaId(int mediaId);

  // get reviews for a user and a media
  @Query("SELECT r FROM ReviewEntity r WHERE r.media.id = ?1 AND r.user = ?2")
  Optional<ReviewEntity> findByMediaIdAndUserEmail(int mediaId, String email);
}