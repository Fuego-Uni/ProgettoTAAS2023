package com.notflix.servicechat.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "message")
public class MessageEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonBackReference
  private ChatEntity chat;

  private String email;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Date timestamp;

  @Column(nullable = true)
  private Long image; 

  // set
  public void setId(Long id) {
    this.id = id;
  }

  // set message
  public void setContent(String content) {
    this.content = content;
  }

  // set chatid
  public void setChatId(ChatEntity chatId) {
    this.chat = chatId;
  }

  // set email
  public void setEmail(String email) {
    this.email = email;
  }

  // set image
  public void setImage(Long image) {
    this.image = image;
  }

  // get message
  public String getContent() {
    return content;
  }

  // get chatid
  public ChatEntity getChatId() {
    return chat;
  }

  // get email
  public String getEmail() {
    return email;
  }

  // get id
  public Long getId() {
    return id;
  }

  // get date
  public Date getTimestamp() {
    return timestamp;
  }

  // get image
  public Long getImage() {
    return image;
  }

  // set Date
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
}
