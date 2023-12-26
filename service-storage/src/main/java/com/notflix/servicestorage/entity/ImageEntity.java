package com.notflix.servicestorage.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "imageData")
public class ImageEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // unique name for the image
  @Column(name = "name", nullable = false, unique = true)
  private String name;

  private String user;

  @Lob
  // @Column(name = "imagedata")
  @Column(name = "imagedata", columnDefinition = "LONGBLOB")
  private byte[] imageData;

  public ImageEntity() {
  }

  public ImageEntity(String name, String user, byte[] imageData) {
    this.name = name;
    this.user = user;
    this.imageData = imageData;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getUser() {
    return user;
  }

  public byte[] getImageData() {
    return imageData;
  }

  public void setImageData(byte[] imageData) {
    this.imageData = imageData;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setUser(String user) {
    this.user = user;
  }
}