package com.notflix.servicereviews.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class ReviewEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user")
  private String user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "media")
  private MediaEntity media;

  @Column(name = "vote")
  private Integer vote;

  @Column(name = "note")
  private String note;

  public ReviewEntity() {
  }

  public ReviewEntity(String user, MediaEntity media, Integer vote, String note) {
    this.user = user;
    this.media = media;
    this.vote = vote;
    this.note = note;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public MediaEntity getMedia() {
    return media;
  }

  public void setMedia(MediaEntity media) {
    this.media = media;
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

  @Override
  public String toString() {
    return "ReviewEntity{" +
      "id=" + id +
      ", user='" + user + '\'' +
      ", media=" + media +
      ", vote=" + vote +
      ", note='" + note + '\'' +
      '}';
  }
}
