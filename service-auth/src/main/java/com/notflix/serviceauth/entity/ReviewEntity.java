package com.notflix.serviceauth.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "reviews")
public class ReviewEntity {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "email")
 private UserEntity user;

 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "film_id")
 private FilmEntity film;

 @Column(name = "vote")
 private Integer vote;

 @Column(name = "note")
 private String note;

 public ReviewEntity() {}

 public ReviewEntity(UserEntity user, FilmEntity film, Integer vote, String note) {
  this.user = user;
  this.film = film;
  this.vote = vote;
  this.note = note;
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public UserEntity getUser() {
  return user;
 }

 public void setUser(UserEntity user) {
  this.user = user;
 }

 public FilmEntity getFilm() {
  return film;
 }

 public void setFilm(FilmEntity film) {
  this.film = film;
 }

 public Integer getVote() {
  return vote;
 }

 public void setVote(Integer vote) {
  this.vote = vote;
 }

 public String getNote() {
  return note;
 }

 public void setNote(String note) {
  this.note = note;
 }
}
