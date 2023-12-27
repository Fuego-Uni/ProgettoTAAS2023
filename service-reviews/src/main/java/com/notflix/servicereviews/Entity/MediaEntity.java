package com.notflix.servicereviews.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "media")
public class MediaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "media_id")
  private int media_id;

  private String type;

  // Add other fields as needed

  public MediaEntity() {
    // Default constructor required by JPA
  }

  public MediaEntity(int media_id, String type) {
    this.media_id = media_id;
    this.type = type;
  }

  public int getId() {
    return id;
  }

  public int getMediaId() {
    return media_id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setMediaId(int media_id) {
    this.media_id = media_id;
  }
}
