package com.notflix.servicereviews.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notflix.servicereviews.entity.MediaEntity;

public interface MediaEntityRepo extends JpaRepository<MediaEntity, Integer> {
  // find by id and type
  @Query("SELECT m FROM MediaEntity m WHERE m.media_id = ?1 AND m.type = ?2")
  Optional<MediaEntity> findByIdAndType(int id, String type);
}

