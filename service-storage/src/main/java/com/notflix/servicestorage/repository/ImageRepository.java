package com.notflix.servicestorage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.notflix.servicestorage.entity.ImageEntity;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
  List<ImageEntity> findByUser(String user);
}