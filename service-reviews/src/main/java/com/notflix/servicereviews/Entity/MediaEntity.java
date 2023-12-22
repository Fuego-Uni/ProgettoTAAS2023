package com.notflix.servicereviews.Entity;
import jakarta.persistence.*;



@Entity
@Table(name = "media")
public class MediaEntity {
 @Id
 @Column(name = "media_id")
 private int id;

 // Add other fields as needed

 public MediaEntity() {
  // Default constructor required by JPA
 }

 public MediaEntity(int id) {
  this.id = id;
 }

 public int getId() {
  return id;
 }

 public void setId(int id) {
  this.id = id;
 }
}
