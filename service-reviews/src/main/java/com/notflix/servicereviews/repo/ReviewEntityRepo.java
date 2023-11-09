package com.notflix.servicereviews.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notflix.servicereviews.Entity.ReviewEntity;

public interface ReviewEntityRepo extends JpaRepository<ReviewEntity, Long> {
 Optional<ReviewEntity> findById(Long id);
}