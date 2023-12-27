package com.notflix.servicereviews.repo;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notflix.servicereviews.entity.ReviewEntity;

public interface ReviewEntityRepo extends JpaRepository<ReviewEntity, Long> {
  @Query("SELECT r FROM ReviewEntity r WHERE r.media.media_id = ?1")
  List<ReviewEntity> findByMediaId(int mediaId);

  // find by media id and type  
  @Query("SELECT r FROM ReviewEntity r WHERE r.media.media_id = ?1 AND r.media.type = ?2")
  List<ReviewEntity> findByMediaIdAndType(int mediaId, String type);

  // get reviews for a user and a media
  @Query("SELECT r FROM ReviewEntity r WHERE r.media.id = ?1 AND r.user = ?2")
  Optional<ReviewEntity> findByIdandEmail(int id, String email);
}