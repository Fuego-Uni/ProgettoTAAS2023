package com.notflix.servicereviews.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notflix.servicereviews.Entity.FilmEntity;

public interface FilmEntityRepo extends JpaRepository<FilmEntity, Integer> {
  

}

