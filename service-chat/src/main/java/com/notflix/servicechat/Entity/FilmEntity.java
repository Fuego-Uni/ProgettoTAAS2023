package com.notflix.servicechat.Entity;
import jakarta.persistence.*;



@Entity
@Table(name = "film")
public class FilmEntity {
 @Id
 @Column(name = "film_id")
 private int filmId;

 // Add other fields as needed

 public FilmEntity() {
  // Default constructor required by JPA
 }

 public FilmEntity(int filmId) {
  this.filmId = filmId;
 }

 public int getFilmId() {
  return filmId;
 }

 public void setFilmId(int filmId) {
  this.filmId = filmId;
 }
}
