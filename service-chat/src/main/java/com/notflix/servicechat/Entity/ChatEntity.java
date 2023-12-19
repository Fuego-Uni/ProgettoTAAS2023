package com.notflix.servicechat.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "chat")
public class ChatEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String user1;

  private String user2;

  @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JsonManagedReference
  private List<MessageEntity> messages;

  // set and get methods
  public void setUser1(String user1) {
    this.user1 = user1;
  }

  public void setUser2(String user2) {
    this.user2 = user2;
  }

  // get Id
  public Long getId() {
    return id;
  }

  // get user1
  public String getUser1() {
    return user1;
  }

  // get user2
  public String getUser2() {
    return user2;
  }

  // get messages
  public List<MessageEntity> getMessages() {
    return messages;
  }

  // set messages
  public void setMessages(List<MessageEntity> messages) {
    this.messages = messages;
  }

}
