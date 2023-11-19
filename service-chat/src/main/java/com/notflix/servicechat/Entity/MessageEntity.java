package com.notflix.servicechat.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    @ManyToOne
    private ChatEntity chat;

    @ManyToOne
    private UserEntity sender;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

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
    // set user
    public void setUser(UserEntity user) {
        this.sender = user;
    }
}
