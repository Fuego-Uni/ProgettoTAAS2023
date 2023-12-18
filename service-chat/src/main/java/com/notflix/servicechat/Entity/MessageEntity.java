package com.notflix.servicechat.Entity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    // set
    public void setId(Long id) {
        this.id = id;
    }
    // set message
    public void setMessage(String message) {
        this.content = message;
    }
    // set chatid
    public void setChatId(ChatEntity chatId) {
        this.chat = chatId;
    }
    // set email
    public void setEmail(String email) {
        this.email = email;
    }
    // get message
    public String getMessage() {
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
    //set Date
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
