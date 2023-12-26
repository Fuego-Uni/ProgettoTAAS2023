package com.notflix.servicereviews.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notflix.servicereviews.entity.MediaEntity;

public interface MediaEntityRepo extends JpaRepository<MediaEntity, Integer> {
  

}

