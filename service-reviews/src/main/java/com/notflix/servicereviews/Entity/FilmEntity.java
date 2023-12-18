package com.notflix.servicereviews.Entity;
import jakarta.persistence.*;



@Entity
@Table(name = "film")
public class FilmEntity {
 @Id
 @Column(name = "film_id")
 private int id;

 // Add other fields as needed

 public FilmEntity() {
  // Default constructor required by JPA
 }

 public FilmEntity(int id) {
  this.id = id;
 }

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }
}
