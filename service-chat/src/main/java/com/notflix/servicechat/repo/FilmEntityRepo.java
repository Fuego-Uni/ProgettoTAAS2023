package com.notflix.servicechat.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.notflix.servicechat.Entity.FilmEntity;

public interface FilmEntityRepo extends JpaRepository<FilmEntity, Integer> {
  

}

